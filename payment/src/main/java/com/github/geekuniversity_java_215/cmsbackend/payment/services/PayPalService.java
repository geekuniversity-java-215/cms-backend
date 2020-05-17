package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.core.data.constants.CorePropNames;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.core.utils.EnvStringBuilder;
import com.github.geekuniversity_java_215.cmsbackend.mail.services.MailService;
import com.github.geekuniversity_java_215.cmsbackend.payment.configurations.PayPalAccount;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.github.geekuniversity_java_215.cmsbackend.payment.data.constants.PaymentPropNames.*;

@Service
@Slf4j
public class PayPalService {

    private final EnvStringBuilder envStringBuilder;
    private final PayPalAccount payPalAccount;
    private final MailService mailService;
    private final AccountService accountService;

    //private String stringUrls;

    private String redirectSuccess;
    private String redirectCancel;



    @Autowired
    public PayPalService(EnvStringBuilder envStringBuilder,
                         PayPalAccount payPalAccount,
                         MailService mailService,
                         AccountService accountService){
        this.envStringBuilder = envStringBuilder;
        this.payPalAccount = payPalAccount;
        this.mailService = mailService;
        this.accountService=accountService;
    }

    @PostConstruct
    private void postConstruct() throws URISyntaxException {

        redirectSuccess = envStringBuilder.buildURL(CONTROLLER_SUCCESS_PATH);
        redirectCancel = envStringBuilder.buildURL(CONTROLLER_CANCEL_PATH);
    }

    // формирую платеж
    public String authorizePayment(String clientId, BigDecimal tax) throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = new RedirectUrls();

        redirectUrls.setReturnUrl(UriComponentsBuilder.fromUriString(redirectSuccess).buildAndExpand(clientId).toUriString());
        redirectUrls.setCancelUrl(UriComponentsBuilder.fromUriString(redirectCancel).buildAndExpand(clientId).toUriString());

        List<Transaction> listTransaction = getTransactionInformation(clientId, tax);

        Payment payment = getPayment(payer, redirectUrls, listTransaction);

        APIContext apiContext = getApiContext();

        Payment approvedPayment = payment.create(apiContext);
        return getApprovalLink(approvedPayment, clientId);
    }


    private Payment getPayment(Payer payer, RedirectUrls redirectUrls, List<Transaction> listTransaction) {
        Payment payment = new Payment();

        //устанавливаю метод оплаты=paypal
        payment.setPayer(payer);

        //устанавливаю редирект, если платеж оформлен или получен отказ
        payment.setRedirectUrls(redirectUrls);

        //устанавливаю сформированный список транзакций
        payment.setTransactions(listTransaction);
        payment.setIntent("sale");
        return payment;
    }

    /*
    устанавливаю метод оплаты=paypal
     */
    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        return payer;
    }


    /*
    подготавливаю список транзакций при выполнении платежа
     */
    private List<Transaction> getTransactionInformation(String clientId, BigDecimal tax) {
        Transaction transaction = getTransaction(clientId, tax);
        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);
        return listTransaction;
    }

    private Transaction getTransaction(String clientId, BigDecimal tax) {
        Transaction transaction = new Transaction();
        transaction.setAmount(getAmount(tax));
        transaction.setDescription("Пополнение счета клиентом id = " + clientId + " на сумму " + tax);
        return transaction;
    }

    /*
    метод для обработки link`ов, полученных после выполнения платежа
     */
    private String getApprovalLink(Payment approvedPayment, String clientId) {

        log.info("ID платежа" + approvedPayment.getId());
        
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;
        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }
        return approvalLink;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = getApiContext();
        return Payment.get(apiContext, paymentId);
    }

    /*
     Деньги списаны, но платеж еще не выполнен.
     В этом методе подтверждаю платеж:
    */
    public String executePayment(String paymentId, String payerId, User user) throws PayPalRESTException {

        String result = null;

        APIContext apiContext = getApiContext();



        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = getPaymentExecution(payerId);
        Payment executedPayment = payment.execute(apiContext, paymentExecution);
        BigDecimal amount = getAmount(executedPayment);

        if (executedPayment.getState().equals("approved")) {
            accountService.addBalance(user.getAccount(), amount, CurrencyCode.RUB);
            mailService.sendPaymentSuccess(user, amount);
            result = "На ваш счет зачислена сумма " + amount;
        } else {
            result =  "Что-то пошло не так при пополнении счета";
        }
        return result;
    }

    private PaymentExecution getPaymentExecution(String payerId) {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return paymentExecution;
    }

    private BigDecimal getAmount(Payment executedPayment) {
        Transaction transaction = executedPayment.getTransactions().get(0);
        return new BigDecimal(transaction.getAmount().getTotal());
    }

    private Amount getAmount(BigDecimal tax) {
        Amount amount = new Amount();
        amount.setCurrency("RUB");
        amount.setTotal(tax.toString());
        return amount;
    }

    private APIContext getApiContext() {
        return new APIContext(
            payPalAccount.getClientId(),
            payPalAccount.getClientSecret(),
            payPalAccount.getMode());
    }

}

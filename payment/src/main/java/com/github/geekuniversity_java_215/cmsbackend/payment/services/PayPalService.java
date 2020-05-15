package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.core.data.constants.CorePropNames;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.core.utils.EnvStringBuilder;
import com.github.geekuniversity_java_215.cmsbackend.mail.services.MailService;
import com.github.geekuniversity_java_215.cmsbackend.payment.configurations.PayPalAccount;
import com.github.geekuniversity_java_215.cmsbackend.payment.data.constants.PaymentPropNames;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
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

    private String stringUrls;



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
    private void postConstruct(){
        stringUrls = buildStringURLS();
    }

    // формирую платеж
    public String authorizePayment(String clientId, Integer tax) throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs(clientId);
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
    подготавливаю ссылки
    Не уверен, что paypal должен возвращать такие ссылки
     */
    private RedirectUrls getRedirectURLs(String clientId) {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(stringUrls + "cancel");
        redirectUrls.setReturnUrl(stringUrls + "success/" + clientId);
        return redirectUrls;
    }


    private String buildStringURLS() {
        return envStringBuilder.buildURL(CONTROLLER_EXECUTE_PAYMENT_PATH);
    }

    /*
    подготавливаю список транзакций при выполнении платежа
     */
    private List<Transaction> getTransactionInformation(String clientId, Integer tax) {
        Transaction transaction = getTransaction(clientId, tax);
        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);
        return listTransaction;
    }

    private Transaction getTransaction(String clientId, Integer tax) {
        Transaction transaction = new Transaction();
        transaction.setAmount(getAmount(tax));
        transaction.setDescription("Пополнение счета клиентом id = " + clientId + " на сумму " + tax);
        return transaction;
    }

    private Amount getAmount(Integer tax) {
        Amount amount = new Amount();
        amount.setCurrency("RUB");
        amount.setTotal(String.valueOf(tax));
        return amount;
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
        APIContext apiContext = getApiContext();

        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = getPaymentExecution(payerId);
        Payment executedPayment = payment.execute(apiContext, paymentExecution);
        BigDecimal amount = getAmount(executedPayment);

        if (executedPayment.getState().equals("approved")) {
            accountService.addBalance(user.getAccount(), amount, CurrencyCode.RUB);
            mailService.sendPaymentSuccess(user, amount);
            return "На ваш счет зачислена сумма " + amount;
        } else {
            return "Что-то пошло не так при пополнении счета";
        }
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

    private APIContext getApiContext() {
        return new APIContext(
            payPalAccount.getClientId(),
            payPalAccount.getClientSecret(),
            payPalAccount.getMode());
    }

}

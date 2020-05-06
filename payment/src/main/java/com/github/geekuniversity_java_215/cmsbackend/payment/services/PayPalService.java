package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PayPalService {
    private static String CLIENT_ID;
    private static String CLIENT_SECRET;
    private static String MODE;
    private static String SERVER_PORT;
    private static String CONTEXT_PATH;

    @Value("${paypal.clientId}")
    public void setClientId(String clientId) {
        PayPalService.CLIENT_ID = clientId;
    }

    @Value("${paypal.clientSecret}")
    public void setClientSecret(String clientSecret) {
        CLIENT_SECRET = clientSecret;
    }

    @Value("${paypal.mode}")
    public void setMODE(String MODE) {
        PayPalService.MODE = MODE;
    }

    @Value("${server.port}")
    public void setServerPort(String port){SERVER_PORT=port;}

    @Value("${server.servlet.context-path}")
    public void setPath(String path){CONTEXT_PATH=path;}

    /*
формирую платеж
 */
    public String authorizePayment(String clientId, Integer tax) throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs(clientId);
        List<Transaction> listTransaction = getTransactionInformation(clientId,tax);

        Payment payment = getPayment(payer, redirectUrls, listTransaction);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = payment.create(apiContext);
              return getApprovalLink(approvedPayment,clientId);
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
        redirectUrls.setCancelUrl("http://localhost:"+SERVER_PORT+CONTEXT_PATH+"/paypal/cancel");
        redirectUrls.setReturnUrl("http://localhost:"+SERVER_PORT+CONTEXT_PATH+"/paypal/success/"+clientId);
        return redirectUrls;
    }

    /*
    подготавливаю список транзакций при выполнении платежа
     */
    private List<Transaction> getTransactionInformation(String clientId, Integer tax) {
        Transaction transaction = getTransaction(clientId, tax);
        log.info("Пополнение счета клиентом id="+clientId+" на сумму "+tax);
        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);
        return listTransaction;
    }

    private Transaction getTransaction(String clientId, Integer tax) {
        Transaction transaction = new Transaction();
        transaction.setAmount(getAmount(tax));
        transaction.setDescription("Пополнение счета клиентом id="+clientId+" на сумму "+tax);
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
    private String getApprovalLink(Payment approvedPayment,String clientId) {
        log.info("ID платежа"+approvedPayment.getId());
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
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    /*
Деньги списаны, но платеж еще не выполнен.
В этом методе подтверждаю платеж:
 */
    public String executePayment(String paymentId, String payerId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment executedPayment=payment.execute(apiContext, paymentExecution);

        Transaction transaction=executedPayment.getTransactions().get(0);
        String amount=transaction.getAmount().getTotal();
        log.info("На ваш счет зачислена сумма " + amount);
        //todo отправить запрос в метод зачисления условных единиц на счет клиента
        if (executedPayment.getState().equals("approved")) {

            return "На ваш счет зачислена сумма " + amount;
            //todo настроить отправку почтового уведомления

        } else {
            return "Что-то пошло не так при пополнении счета";
        }

    }
}

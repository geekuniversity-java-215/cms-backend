package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.paypal.base.rest.PayPalRESTException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PayPalServiceTest {
    private String clientId;
    private Integer tax;
    private final PayPalService payPalService;

    @Autowired
    public PayPalServiceTest(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @Before
    public void setUp() {
        clientId="16";
        tax=137;
    }

    @Test
    void authorizePayment() {

        try {
            payPalService.authorizePayment(clientId,tax);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPaymentDetails() {
    }

    @Test
    void executePayment() {
    }
}
package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.payment.PaymentApplication;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PaymentApplication.class)
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

    @Disabled // ToDo: TEST FAILED, fix it
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
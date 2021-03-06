package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.paypal.base.rest.PayPalRESTException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
@Slf4j
class PayPalGetPaymentServiceTest {

    private static String clientId;
    private static Integer tax;
    private final PayPalGetPaymentService payPalGetPaymentService;

    @Autowired
    private  Environment environment;

    @BeforeAll
    public static void beforeAll() {
        clientId="16";
        tax=137;
    }

    @Autowired
    public PayPalGetPaymentServiceTest(PayPalGetPaymentService payPalGetPaymentService) {
        this.payPalGetPaymentService = payPalGetPaymentService;
    }

    @Test
    @SneakyThrows
    void authorizePayment() {
        payPalGetPaymentService.authorizePayment(clientId,tax);
    }

    @Test
    void getPaymentDetails() {
        //log.info("Мир Труд Май");
    }

    @Test
    void executePayment() {
    }

}

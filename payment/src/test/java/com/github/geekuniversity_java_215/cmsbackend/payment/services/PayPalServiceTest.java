package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.payment.controllers.PaymentController;
import com.paypal.base.rest.PayPalRESTException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@SpringBootTest
@Slf4j
class PayPalServiceTest {

    private static String clientId;
    private static Integer tax;
    private final PayPalService payPalService;

    @Autowired
    private  Environment environment;

    @BeforeAll
    public static void beforeAll() {
        clientId="16";
        tax=137;
    }

    @Autowired
    public PayPalServiceTest(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @Test
    @SneakyThrows
    void authorizePayment() {
        try {
            payPalService.authorizePayment(clientId,tax);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPaymentDetails() {
        //log.info("Мир Труд Май");
    }

    @Test
    void executePayment() {
    }

}

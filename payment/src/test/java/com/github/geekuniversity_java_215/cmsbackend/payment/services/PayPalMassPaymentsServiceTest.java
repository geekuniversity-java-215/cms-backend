package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.utils.Junit5Extension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith({Junit5Extension.class})
@Slf4j
class PayPalMassPaymentsServiceTest {
    private final PayPalMassPaymentsService payPalMassPaymentsService;
    private static String userMail;
    private static String currencyCodeType ;
    private static String amount ;

    @Autowired
    public PayPalMassPaymentsServiceTest(PayPalMassPaymentsService payPalMassPaymentsService) {
        this.payPalMassPaymentsService = payPalMassPaymentsService;
    }

    @BeforeEach
    public void setUp() {
        userMail = "KevinNesh@ya.ru";
        currencyCodeType = "RUB";
        amount = "111";
    }

    @Test
    void doMassPayment() {
        // FixMe args
        //payPalMassPaymentsService.doMassPayment(userMail,amount,currencyCodeType);
    }
}
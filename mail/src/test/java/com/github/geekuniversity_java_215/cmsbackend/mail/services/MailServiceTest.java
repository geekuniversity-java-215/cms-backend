package com.github.geekuniversity_java_215.cmsbackend.mail.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Customer;
import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.Promise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@SpringBootTest
//@ActiveProfiles("dev")
@Slf4j
class MailServiceTest {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private MailService mailService;


    @BeforeEach
    void setUp() {

    }
    //@Disabled
    @Test
    void sendRegConfirmation() throws ExecutionException, InterruptedException {

        // отправляем письмо самому себе
        Customer cus = new Customer("Вася", "Пупкин", "cmsbackendgeek@gmail.com","12345");

        String tokenUrl = mailService.generateConfirmationUrl(cus);
        Promise promise = mailService.sendRegistrationConfirmation(cus, tokenUrl);
        promise.get();
        log.info("Message send");
    }


    @Test
    void sendPaymentSuccess() throws ExecutionException, InterruptedException {
        // отправляем письмо об успешном зачислении платежа
        Account acc = new Account();
        Customer customer = new Customer();
        customer.setFirstName("Вася"); customer.setLastName("Пупкин"); customer.setEmail("m@m.ru"); customer.setPhoneNumber("123");
        customer.setAccount(acc);

        Promise promise=mailService.sendPaymentSuccess(customer, BigDecimal.valueOf(137));
        promise.get();
        log.info("sendPaymentSuccess success");
    }
}

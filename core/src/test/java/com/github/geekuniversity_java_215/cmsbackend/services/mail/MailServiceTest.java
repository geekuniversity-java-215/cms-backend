package com.github.geekuniversity_java_215.cmsbackend.services.mail;

import com.github.geekuniversity_java_215.cmsbackend.entites.Customer;
import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.Promise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutionException;

@SpringBootTest
@Slf4j
class MailServiceTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    MailService mailService;

    @Test
    void sendRegConfirmation() throws ExecutionException, InterruptedException {

        Customer cus = new Customer("Вася", "Пупкин", "vasya@yandex.ru","12345");

        String tokenUrl = mailService.generateConfirmationUrl(cus);
        Promise promise = mailService.sendRegistrationConfirmation(cus, tokenUrl);
        promise.get();
        log.info("Message send");

    }
}
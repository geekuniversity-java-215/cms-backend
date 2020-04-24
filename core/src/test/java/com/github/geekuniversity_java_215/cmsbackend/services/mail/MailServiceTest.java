package com.github.geekuniversity_java_215.cmsbackend.services.mail;

import com.github.geekuniversity_java_215.cmsbackend.entites.Customer;
import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.Promise;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutionException;

@SpringBootTest
@ActiveProfiles("dev")
@Slf4j
class MailServiceTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    MailService mailService;


    @Disabled
    @Test
    void sendRegConfirmation() throws ExecutionException, InterruptedException {

        // отправляем письмо самому себе
        Customer cus = new Customer("Вася", "Пупкин", "cmsbackendgeek@gmail.com","12345");

        String tokenUrl = mailService.generateConfirmationUrl(cus);
        Promise promise = mailService.sendRegistrationConfirmation(cus, tokenUrl);
        promise.get();
        log.info("Message send");

    }
}
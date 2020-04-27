package com.github.geekuniversity_java_215.cmsbackend.mail.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Customer;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.core.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.Promise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@SpringBootTest
@Slf4j
class MailServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private MailService mailService;



    @Test
    void personSendRegConfirmation() throws ExecutionException, InterruptedException {
        Person person = personService.findById(1L).get();
        String tokenURL = mailService.generateConfirmationUrl(person);

        // отправляем письмо самому себе
        Promise<Void> promise = mailService.sendRegistrationConfirmation(person, tokenURL);
        promise.get();
        log.info("Message send");
    }


    @Test
    void sendPaymentSuccess() throws ExecutionException, InterruptedException {

        // отправляем письмо об успешном зачислении платежа
        Person person = personService.findById(1L).get();
        Promise<Void> promise=mailService.sendPaymentSuccess(person, BigDecimal.valueOf(1337));
        promise.get();
        log.info("sendPaymentSuccess success");
    }
}

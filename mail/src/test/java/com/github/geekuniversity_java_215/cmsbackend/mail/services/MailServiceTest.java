package com.github.geekuniversity_java_215.cmsbackend.mail.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.Promise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import static com.pivovarit.function.ThrowingSupplier.unchecked;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@SpringBootTest
@Slf4j
class MailServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;



    @Test
    void userSendRegConfirmation() throws ExecutionException, InterruptedException {
        UnconfirmedUser user = new UnconfirmedUser("newuser",
            "newuser_password", "cmsbackendgeek@gmail.com", "Новый", "newuser@mail.ru", "932494356678");

        // отправляем письмо самому себе
        URI uri =  unchecked(()->new URI("https://natribu.org/ru/")).get();

        Promise<Void> promise = mailService.sendRegistrationConfirmation(user, uri);
        promise.get();
        log.info("Message send");
    }


    @Test
    void sendPaymentSuccess() throws ExecutionException, InterruptedException {

        // отправляем письмо об успешном зачислении платежа
        User user = userService.findById(1L).get();
        Promise<Void> promise=mailService.sendPaymentSuccess(user, BigDecimal.valueOf(1337));
        promise.get();
        log.info("sendPaymentSuccess success");
    }
}

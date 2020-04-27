package com.github.geekuniversity_java_215.cmsbackend.mail.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.utils.JobPool;
import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.Promise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;

@Service
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MailMessageBuilder messageBuilder;
    private final JobPool<Void> jobPool;

    @Autowired
    public MailService(JavaMailSender javaMailSender, MailMessageBuilder messageBuilder) {
        this.javaMailSender = javaMailSender;
        this.messageBuilder = messageBuilder;

        jobPool = new JobPool<>("SendMail",2, Duration.ofSeconds(60), null);
    }



    /**
     * Отправляет письмо об успешном платеже
     * @param person Person
     * @param amount сумма платежа
     */
    public Promise sendPaymentSuccess(Person person, BigDecimal amount) {

        log.trace("Отправляем письмо о успешно проведенном платеже");
        System.out.println(person.getAccount());
        final String email = person.getEmail();
        final String subject = "Платеж успешно проведен";
        final String body = messageBuilder.buildPaymentSuccess(person,amount);
        return sendMessage(email, subject, body);
    }

    /**
     * Отправляет письмо с подтверждением о регистрации
     * @param person Person
     * @param confirmationUrl сслка для завершения регистрации
     * @return
     */
    public Promise<Void> sendRegistrationConfirmation(Person person, String confirmationUrl) {

        //ToDo: нужно убедиться, что формируется нормальный url из сервиса авторизации
        log.trace("Отправляем письмо о успешной регистрации");
        final String email = person.getEmail();
        final String subject = "Завершение регистрации";
        final String body = messageBuilder.buildRegistrationConfirmationEmail(person, confirmationUrl);

        return sendMessage(email, subject, body);
    }


    //ToDo: generateConfirmationUrl перенести в сервис авторизации
    /**
     * метод формирует url для подтверждения регистрации, перенести в сервис авторизации
     */
    public String generateConfirmationUrl(Person person) {
        String token = new BigInteger(130, new SecureRandom()).toString(32);

        // ToDo: сгенерированный url является ключом. необходимо сохранить для пользователя,
        // чтобы потом провести проверки для завершения регистрации
        // Токен должен протухать через некоторое время - хранить в отдельной таблице registrationToken,
        // запускать @Scheduled чтобы прибивать пользователей, которые не подтвердили регистрацию
        // и их записи в табле registrationToken


        // Add token to cache

        // ToDo: Move url to core.data.constants, include host and port vars from application.properties
        //return "http://localhost:8189/app/registration/confirmation/" + token;
        return "https://natribu.org/";
    }


    /**
     * Send custom message
     * @param email sendTo email address
     * @param subject mail subject
     * @param body message body
     * @return Promise, use Promise.get() to wait till mail have send
     */
    public Promise<Void> sendMessage(String email, String subject, String body) {

        Promise<Void> result = null;

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        try {
            helper.setTo(email);
            helper.setText(body, true);
            helper.setSubject(subject);
        } catch (MessagingException e) {
            log.error("MimeMessageHelper error:", e);
        }

        try {
            result = jobPool.add(() -> javaMailSender.send(message));
        }
        catch (Exception e) {
            log.error("JobPool " + jobPool.getPoolName() + " error:", e);
        }
        return result;
    }

}

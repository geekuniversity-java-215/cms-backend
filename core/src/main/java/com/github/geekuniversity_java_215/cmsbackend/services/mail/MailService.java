package com.github.geekuniversity_java_215.cmsbackend.services.mail;

import com.github.geekuniversity_java_215.cmsbackend.entites.Order;
import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.utils.JobPool;
import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
@Slf4j
public class MailService {
    private JavaMailSender javaMailSender;
    private MailMessageBuilder messageBuilder;
    private JobPool<Void> jobPool;

    @Autowired
    public MailService(JavaMailSender javaMailSender, MailMessageBuilder messageBuilder) {
        this.javaMailSender = javaMailSender;
        this.messageBuilder = messageBuilder;

        jobPool = new JobPool<>("SendMail",2, Duration.ofSeconds(60), null);
    }

    /**
     * Отправляет письмо об успешном платеже
     * - НЕ ГОТОВО
     */
    public void sendPaymentSuccess(Order order) {

        //todo добавить в метод execute email получателя, текст сообщения, сформированное письмо и отправителя
//        log.trace("Отправляем письмо о успешно проведенном платеже");
//
//        final String email = person.getEmail();
//        final String subject = "Платеж успешно проведен";
//        final String body = messageBuilder.buildPaymentSuccess(order.getId());
//        jobPool.add(() -> sendMessage(email, subject, body));



        //taskExecutor.execute(new scheduleSendEmail("cmsbackendgeek@gmail.com", "Завершение регистрации", , sender));
        //taskExecutor.execute(new scheduleSendEmail(email, subject, messageBuilder.buildPaymentSuccess("clientId"), sender));
        //jobPool.addRunnable(new scheduleSendEmail(email, subject, messageBuilder.buildPaymentSuccess("clientId"), sender),taskExecutor);
    }


    /**
     * Отправляет письмо с подтверждением о регистрации
     *
     * confirnmationUrl = generateConfirmationUrl(person)
     */
    public Promise sendRegistrationConfirmation(Person person, String confirmationUrl) {

        //todo почту получателя достать из сущности регистрируемого пользователя.
        // В тему добавить ник пользователя, сформировать письмо с нормальным url`ом

        log.trace("Отправляем письмо о успешной регистрации");
        final String email = person.getEmail();
        final String subject = "Завершение регистрации";
        final String body = messageBuilder.buildRegistrationConfirmationEmail(person, confirmationUrl);

        return sendMessage(email, subject, body);
    }


    /**
     * метод формирует url для подтверждения регистрации, перенести в сервис авторизации
     */
    //ToDo: generateConfirmationUrl перенести в сервис авторизации
    public String generateConfirmationUrl(Person person) {
        String token = new BigInteger(130, new SecureRandom()).toString(32);

        // ToDo: сгенерированный url является ключом. необходимо сохранить для пользователя,
        // чтобы потом провести проверки для завершения регистрации
        // Токен должен протухать через некоторое время - Guava Cache
        // https://www.baeldung.com/guava-cache

        // Add token to cache

        // ToDo: Move url to variable, include host and port vars from application.properties
        //return "http://localhost:8189/app/registration/confirmation/" + token;
        return "https://natribu.org/";
    }


    // ==================================================================================================================


    private Promise sendMessage(String email, String subject, String body) {

        Promise result = null;

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

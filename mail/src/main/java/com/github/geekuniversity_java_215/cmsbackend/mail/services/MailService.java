package com.github.geekuniversity_java_215.cmsbackend.mail.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
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
import java.net.URI;
import java.time.Duration;
import java.util.Optional;

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
     * @param user User
     * @param amount сумма платежа
     */
    public Promise<Void> sendPaymentSuccess(Optional<User> user, BigDecimal amount) {
        final String email = user.get().getEmail();
        final String subject = "Платеж успешно проведен";
        final String body = messageBuilder.buildPaymentSuccess(user.get(),amount);
        return sendMessage(email, subject, body);
    }

    /**
     * Отправляет письмо с подтверждением о регистрации
     * @param user User
     * @param confirmationUrl сслка для завершения регистрации
     * @return
     */
    public Promise<Void> sendRegistrationConfirmation(UnconfirmedUser user, URI confirmationUrl) {
        final String email = user.getEmail();
        final String subject = "Завершение регистрации";
        final String body = messageBuilder.buildRegistrationConfirmationEmail(user, confirmationUrl);

        return sendMessage(email, subject, body);
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
            // DISABLED DUE TO GOOGLE QUOTA BAN
            //result = jobPool.add(() -> javaMailSender.send(message));
        }
        catch (Exception e) {
            log.error("JobPool " + jobPool.getPoolName() + " error:", e);
        }
        return result;
    }

}

package com.github.geekuniversity_java_215.cmsbackend.services.impl;

import com.github.geekuniversity_java_215.cmsbackend.services.MailService;
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

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender sender;
    private MailMessageBuilderImpl messageBuilder;
    private TaskExecutor taskExecutor;

    private Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setMessageBuilder(MailMessageBuilderImpl messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    public MailServiceImpl(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void  sendPaymentSuccess() {
        //todo добавить в метод execute email получателя, текст сообщения, сформированное письмо и отправителя
        //taskExecutor.execute(new scheduleSendEmail(order.getUser().getEmail(), String.format("Заказ %d%n отправлен в обработку", order.getId()), messageBuilder.buildPaymentSuccess(clientId), sender));

    }

    public void sendRegConfirmation() {
        //todo добавить в метод execute email получателя, текст сообщения, сформированное письмо и отправителя
        //taskExecutor.execute(new scheduleSendEmail(user.getEmail(), String.format("Завершение регистрации пользователя %s", user.getFirstName()), messageBuilder.buildRegConfirmationEmail(user, urlGenerate(user)), sender));
    }
/*
метод формирует url для подтверждения регистрации
 */
    private String urlGenerate(String user) {
        String url = new BigInteger(130, new SecureRandom()).toString(32);
        //user.setUrl(url); url необходимо сохранить в пользователе, для проверки соответствия при подтверждении
        return "http://localhost:8189/app/registration/confirmation/" + url;
    }

    /*
    шедуллер для отправки писем, чтобы пользователь не ждал
     */
    private static class scheduleSendEmail implements Runnable {
        private String email;
        private String subject;
        private String text;
        private JavaMailSender sender;

        scheduleSendEmail(String email, String subject, String text, JavaMailSender sender) {
            this.email = email;
            this.subject = subject;
            this.text = text;
            this.sender=sender;
        }


        @Override
        public void run() {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            try {
                helper.setTo(email);
                helper.setText(text, true);
                helper.setSubject(subject);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            sender.send(message);
        }
    }


}

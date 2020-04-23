package com.github.geekuniversity_java_215.cmsbackend.services.mail;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MailService {
    private JavaMailSender sender;
    private MailMessageBuilder messageBuilder;
    private TaskExecutor taskExecutor;

    @Autowired
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setMessageBuilder(MailMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    public MailService(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void  sendPaymentSuccess() {
        //todo добавить в метод execute email получателя, текст сообщения, сформированное письмо и отправителя
        //taskExecutor.execute(new scheduleSendEmail(order.getUser().getEmail(), String.format("Заказ %d%n отправлен в обработку", order.getId()), messageBuilder.buildPaymentSuccess(clientId), sender));

    }

    public void sendRegConfirmation() {
        //todo добавить в метод execute email получателя, текст сообщения, сформированное письмо и отправителя


//        new scheduleSendEmail("myomenhope@yandex.ru",
//            "Завершение регистрации пользователя Вася Пупкин",
//            "Test Test Test 2!",
//            sender).run();

        log.info("message send");
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
            this.sender = sender;
        }


        @Override
        public void run() {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            try {
                //helper.setFrom("librarianw40k@yandex.ru");
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

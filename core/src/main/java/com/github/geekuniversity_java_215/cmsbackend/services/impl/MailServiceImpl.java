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
import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.SECONDS;


@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender sender;
    private MailMessageBuilderImpl messageBuilder;
    private TaskExecutor taskExecutor;
    private ThreadPoolExecutor threadPool;
    private JobPool jobPool;
    private Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setMessageBuilder(MailMessageBuilderImpl messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    @Autowired
    public MailServiceImpl(TaskExecutor taskExecutor){
        this.taskExecutor = taskExecutor;
    }
    @Autowired
    public void JobPool (){
        this.jobPool=new JobPool(5, Duration.of(180, SECONDS), new Consumer() {
            @Override
            public void accept(Object o) {
                new scheduleSendEmail("amzhulanov@ya.ru", "Платеж success", messageBuilder.buildPaymentSuccess("clientId"), sender);
            }
        });
    }

    /*
    @taskExecutor.execute - многопоточность CompletbleFuture
    @jobPool.addRunnable - многопоточность с Tascalate
     */
    public void sendPaymentSuccess() {
        //todo добавить в метод execute email получателя, текст сообщения, сформированное письмо и отправителя
        System.out.println(messageBuilder.buildPaymentSuccess("clientId"));
        String email = "amzhulanov@ya.ru";
        String subject = "Платеж success";
        System.out.println("sender = " + sender.toString());

        //taskExecutor.execute(new scheduleSendEmail(email, subject, messageBuilder.buildPaymentSuccess("clientId"), sender));
        jobPool.addRunnable(new scheduleSendEmail(email, subject, messageBuilder.buildPaymentSuccess("clientId"), sender),taskExecutor);
    }

    public void sendRegConfirmation(String client) {
        //todo почту получателя достать из сущности регистрируемого пользователя. В тему добавить ник пользователя, сформировать письмо с нормальным url`ом
        taskExecutor.execute(new scheduleSendEmail("cmsbackendgeek@gmail.com", "Завершение регистрации", messageBuilder.buildRegConfirmationEmail(client, urlGenerate(client)), sender));
    }

    /*
    метод формирует url для подтверждения регистрации
     */
    private String urlGenerate(String user) {
        String url = new BigInteger(130, new SecureRandom()).toString(32);
        //todo сгенерированный url является ключом. необходимо сохранить для пользователя, чтобы потом провести проверки для завершения регистрации
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
                helper.setTo(email);
                helper.setText(text, true);
                helper.setSubject(subject);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            sender.send(message);
        }
    }


//    public Supplier testMail(String email, String subject, String text, JavaMailSender sender) {
//        MimeMessage message = sender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
//        try {
//            helper.setTo(email);
//            helper.setText(text, true);
//            helper.setSubject(subject);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//         sender.send(message);
//        return null;
//    }
}




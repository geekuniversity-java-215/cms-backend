package com.github.geekuniversity_java_215.cmsbackend.mail.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Arrays;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class MailConfig {
    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Autowired
    Environment env;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        //Arrays.stream(env.getActiveProfiles()).noneMatch("default"::equals);
        boolean debugMail = env.getActiveProfiles().length > 0;

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", Boolean.toString(debugMail));
        props.put("mail.from", username);

        return mailSender;
    }
}

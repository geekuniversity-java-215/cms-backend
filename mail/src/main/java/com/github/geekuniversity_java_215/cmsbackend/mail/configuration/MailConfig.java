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
//уже все настройки включены в PropertiesConfiguration
//@PropertySource("classpath:application.properties")
public class MailConfig {
    private final String MAIL_TRANSPORT_PROTOCOL="smtp";
    private final String MAIL_SMTP_AUTH="true";
    private final String MAIL_SMTP_STARTTLS_ENABLE="true";

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private Integer port;

    private final Environment env;

    @Autowired
    public MailConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);


        mailSender.setUsername(username);
        mailSender.setPassword(password);

        boolean debugMail = env.getActiveProfiles().length > 0;

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
        props.put("mail.smtp.auth", MAIL_SMTP_AUTH);
        props.put("mail.smtp.starttls.enable", MAIL_SMTP_STARTTLS_ENABLE);
        props.put("mail.debug", Boolean.toString(debugMail));
        props.put("mail.from", username);

        return mailSender;
    }
}

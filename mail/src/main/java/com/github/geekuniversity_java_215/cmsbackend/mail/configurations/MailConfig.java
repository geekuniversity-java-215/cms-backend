package com.github.geekuniversity_java_215.cmsbackend.mail.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    private final String MAIL_TRANSPORT_PROTOCOL = "smtp";
    private final String MAIL_SMTP_AUTH = "true";
    private final String MAIL_SMTP_STARTTLS_ENABLE = "true";

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private Integer port;

    @Value("${mail.transport.protocol}")
    private String mailProtocol;

    @Value("${mail.smtp.auth}")
    private String mailAuth;

    @Value("${mail.smtp.starttls.enable}")
    private String mailSMTP;

    @Value("${mail.debug}")
    private String mailDebug;

    @Value("${mail.from}")
    private String mailFrom;

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
        props.put(mailProtocol, MAIL_TRANSPORT_PROTOCOL);
        props.put(mailAuth, MAIL_SMTP_AUTH);
        props.put(mailSMTP, MAIL_SMTP_STARTTLS_ENABLE);
        props.put(mailDebug, Boolean.toString(debugMail));
        props.put(mailFrom, username);

        return mailSender;
    }

}

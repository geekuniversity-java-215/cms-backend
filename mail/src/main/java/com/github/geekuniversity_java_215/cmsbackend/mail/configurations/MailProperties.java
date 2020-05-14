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
public class MailProperties {
    private final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private final String MAIL_DEBUG = "mail.debug";
    private final String MAIL_FROM = "mail.from";


    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private Integer port;

    @Value("${mail.transport.protocol}")
    private String mailTransportProtocol;

    @Value("${mail.smtp.auth}")
    private String mailSmtpAuth;

    @Value("${mail.smtp.starttls.enable}")
    private String mailStartTlsEnable;

    private final Environment env;

    @Autowired
    public MailProperties(Environment env) {
        this.env = env;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        boolean debugMail = env.getActiveProfiles().length > 0 &&
            !env.getActiveProfiles()[0].equals("default");

        Properties props = mailSender.getJavaMailProperties();
        props.put(MAIL_TRANSPORT_PROTOCOL, mailTransportProtocol);
        props.put(MAIL_SMTP_AUTH, mailSmtpAuth);
        props.put(MAIL_SMTP_STARTTLS_ENABLE, mailStartTlsEnable);
        props.put(MAIL_DEBUG, Boolean.toString(debugMail));
        props.put(MAIL_FROM, username);

        return mailSender;
    }
}

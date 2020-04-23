package com.github.geekuniversity_java_215.cmsbackend.mailsystem.service;

import com.github.geekuniversity_java_215.cmsbackend.services.mail.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;


@SpringBootTest
class MailServiceTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    MailService mailService;

    @Test
    void sendRegConfirmation() {
        mailService.sendRegConfirmation();
    }
}
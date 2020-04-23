package com.github.geekuniversity_java_215.cmsbackend.services.mail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
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
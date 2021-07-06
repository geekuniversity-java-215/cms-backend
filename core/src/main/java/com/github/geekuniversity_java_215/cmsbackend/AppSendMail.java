package com.github.geekuniversity_java_215.cmsbackend;

import com.github.geekuniversity_java_215.cmsbackend.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppSendMail implements ApplicationRunner {
    private MailService mailService;


    @Autowired
    public void init(MailService mailService){
        this.mailService=mailService;

    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
        mailService.sendPaymentSuccess();
    }
}

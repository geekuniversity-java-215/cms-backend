package com.github.geekuniversity_java_215.cmsbackend.cmsapplication;

import com.github.geekuniversity_java_215.cmsbackend.core.UsersInitializer;
import com.github.geekuniversity_java_215.cmsbackend.core.services.*;
import com.github.geekuniversity_java_215.cmsbackend.mail.services.MailService;
import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.entities.TestEntity;
import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.repositories.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CmsInitializer implements ApplicationRunner {

    private final TestRepository testRepository;
    private final UsersInitializer usersInitializer;

    @Autowired
    public CmsInitializer(TestRepository testRepository,
                          UsersInitializer usersInitializer) {

        this.testRepository = testRepository;
        this.usersInitializer = usersInitializer;
    }

    @Override
    public void run(ApplicationArguments args) {

        //log.info("CmsAppDemoInitializer - add basic entities to DB");

        testRepository.save(new TestEntity("Вася test"));

//        // Only for Tests purposes on H2 database !!!
//        // to handle that each microservice has separate H2 instance
//        // Should't be run on production !
//        log.debug("AuthServer initialize users");
//        usersInitializer.initUsers();
    }

}


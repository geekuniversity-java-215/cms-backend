package com.github.geekuniversity_java_215.cmsbackend.cmsapplication;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.*;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
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

    private final UserService userService;
    private final ClientService clientService;
    private final CourierService courierService;
    private final TestRepository testRepository;
    private final MailService mailService;
    private final OrderService orderService;

    @Autowired
    public CmsInitializer(UserService userService,
                          ClientService clientService,
                          CourierService courierService,
                          TestRepository testRepository,
                          MailService mailService,
                          OrderService orderService) {

        this.userService = userService;
        this.clientService = clientService;
        this.courierService = courierService;
        this.testRepository = testRepository;
        this.mailService = mailService;
        this.orderService = orderService;
    }

    @Override
    public void run(ApplicationArguments args) {

        //log.info("CmsAppDemoInitializer - add basic entities to DB");

        testRepository.save(new TestEntity("Вася test"));
    }
}


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

        log.info("CmsAppDemoInitializer - add basic entities to DB");

        testRepository.save(new TestEntity("Вася test"));

        Address from;
        Address to;
        Order order;

        User user;
        Client client;
        Courier courier;

        // Prepare database here

        try {
            from = new Address("Москва", "Улица красных тюленей", 1, 2, 3);
            to = new Address("Мухосранск", "Западная", 2, 2, 5);

            user = new User("vasya1", "INVALID",
                "Вася1", "Пупкин1", "vasya1@mail.ru", "112232344551");
            userService.save(user);

            client = new Client(user, "CLIENT_DATA");
            clientService.save(client);

            user = new User("sema1", "INVALID",
                "Сема1", "Пасечкин", "sema1@mail.ru", "9087967865431");
            userService.save(user);
            courier = new Courier(user, "COURIER_DATA");
            courierService.save(courier);

            order = new Order();
            order.setFrom(from);
            order.setTo(to);
            order.setStatus(OrderStatus.DONE);
            order.setCourier(courier);
            order.setClient(client);
            orderService.save(order);
        }
        catch (Exception ignore){
            log.info("ну и хрен с ним, это пока отладка");
        }
    }
}


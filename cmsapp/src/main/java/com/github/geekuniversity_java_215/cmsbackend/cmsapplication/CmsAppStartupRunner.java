package com.github.geekuniversity_java_215.cmsbackend.cmsapplication;

import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.entities.TestEntity;
import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.repositories.TestRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.*;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.mail.services.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Component
@Slf4j
public class CmsAppStartupRunner implements ApplicationRunner {

    private final UserService userService;
    private final ClientService clientService;
    private final CourierService courierService;
    private final TestRepository testRepository;
    private final MailService mailService;
    private final OrderService orderService;

    @Autowired
    public CmsAppStartupRunner(UserService userService,
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

        testRepository.save(new TestEntity("Вася test"));
        log.info("TestEntity saved");

        Address from;
        Address to;
        Order order;

        User user;
        Client client;
        Courier courier;

        // Prepare database here
        from = new Address("Москва", "Улица красных тюленей", 1, 2, 3);
        to = new Address("Мухосранск", "Западная", 2, 2, 5);

        user = new User("Вася", "Пупкин", "vasya@mail.ru", "1122334455");
        userService.save(user);

        client = new Client(user, "CLIENT_DATA");
        clientService.save(client);

        user = new User("Сема", "Пасечкин", "sema@mail.ru", "908796786543");
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
}


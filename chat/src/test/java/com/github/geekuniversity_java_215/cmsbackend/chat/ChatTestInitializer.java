package com.github.geekuniversity_java_215.cmsbackend.chat;

import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.*;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.*;
import com.github.geekuniversity_java_215.cmsbackend.core.services.order.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatTestInitializer implements ApplicationRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private OrderService orderService;

    @Override
    public void run(ApplicationArguments args) {
        // Prepare database here

        log.info("ChatTestInitializer started!");

        // INIT DB
        User user = new User("vasya2", "INVALID",
            "Вася2", "Пупкин2", "vasya2@mail.ru", "11223344552");
        user.setAccount(new Account());
        userService.save(user);
        Client client = new Client(user, "CLIENT");
        clientService.save(client);

        user = new User("semen2", "INVALID",
            "Semen2", "Semenov2", "semen2@mail.ru", "466574356457782");
        user.setAccount(new Account());
        userService.save(user);
        Courier courier = new Courier(user, "COURIER");
        courierService.save(courier);

        Order order = new Order();
        order.setClient(client);
        order.setCourier(courier);
        order.setStatus(OrderStatus.TRANSIT);
        orderService.save(order);
    }
}


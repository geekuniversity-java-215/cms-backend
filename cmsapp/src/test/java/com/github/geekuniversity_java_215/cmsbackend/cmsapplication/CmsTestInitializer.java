package com.github.geekuniversity_java_215.cmsbackend.cmsapplication;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
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
public class CmsTestInitializer implements ApplicationRunner {

    private final UserService userService;
    private final AccountService accountService;
    private final OrderService orderService;
    private final ClientService clientService;
    private final CourierService courierService;

    @Autowired
    public CmsTestInitializer(UserService userService,
                              AccountService accountService,
                              OrderService orderService,
                              ClientService clientService,
                              CourierService courierService) {
        this.userService = userService;
        this.accountService = accountService;
        this.orderService = orderService;
        this.clientService = clientService;
        this.courierService = courierService;
    }

    @Override
    public void run(ApplicationArguments args) {

        // Prepare database here

        log.info("CmsTestInitializer - add basic entities to DB for tests");

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
            order.setStatus(OrderStatus.COMPLETED);
            order.setCourier(courier);
            order.setClient(client);
            orderService.save(order);
        }
        catch (Exception ignore){
            log.info("ну и хрен с ним, это пока отладка");
        }
    }
}


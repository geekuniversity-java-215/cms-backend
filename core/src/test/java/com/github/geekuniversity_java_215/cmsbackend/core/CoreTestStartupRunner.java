package com.github.geekuniversity_java_215.cmsbackend.core;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.*;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CoreTestStartupRunner implements ApplicationRunner {

    private final PersonService personService;
    private final AccountService accountService;
    private final OrderService orderService;

    @Autowired
    public CoreTestStartupRunner(PersonService personService,
                                 AccountService accountService,
                                 OrderService orderService) {
        this.personService = personService;
        this.accountService = accountService;
        this.orderService = orderService;
    }

    @Override
    public void run(ApplicationArguments args) {

        Address from;
        Address to;
        Order order;

        Customer customer;
        Courier courier;

        // Prepare database here
        from = new Address("Москва", "Улица красных тюленей", 1, 2, 3);
        to = new Address("Мухосранск", "Западная", 2, 2, 5);

        customer = new Customer("Вася", "Пупкин", "vasya@mail.ru", "1122334455");
        courier = new Courier("Сема", "Пасечкин", "sema@mail.ru", "908796786543", "блабла");

        personService.save(customer);
        personService.save(courier);

        order = new Order();
        order.setFrom(from);
        order.setTo(to);
        order.setStatus(OrderStatus.DONE);
        order.setCourier(courier);
        order.setCustomer(customer);
        orderService.save(order);
    }
}


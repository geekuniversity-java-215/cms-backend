package com.github.geekuniversity_java_215.cmsbackend;

import com.github.geekuniversity_java_215.cmsbackend.entites.*;
import com.github.geekuniversity_java_215.cmsbackend.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.service.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.service.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.service.PersonService;
import com.github.geekuniversity_java_215.cmsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private final AccountService accountService;
    private final PersonService personService;
    private final OrderService orderService;




    public AppStartupRunner(AccountService accountService, PersonService personService, OrderService orderService) {
        this.accountService = accountService;
        this.personService = personService;
        this.orderService = orderService;
    }


    @Override
    public void run(ApplicationArguments args) {

        log.info("\n");
        log.info("Testing logback logging:");
        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");

        // TESTING DB


        Account acc = new Account();
        Customer cus = new Customer();
        cus.setFirstName("Вася");
        cus.setLastName("Пупкин");
        cus.setAccount(acc);
        personService.save(cus);
        log.info("customer id: {}", cus.getId());

        // should be the same
        acc = accountService.findById(1L).get();

        acc = new Account();
        cus = new Customer();

        cus.setFirstName("Семен");
        cus.setLastName("Семенов");
        cus.setAccount(acc);
        personService.save(cus);

        acc = accountService.findById(2L).get();
        log.info("loaded: {} ok!", acc);


        acc = new Account();
        Courier cur = new Courier();
        cur.setFirstName("Zed");
        cur.setLastName("Zed");
        cur.setBlaBla("BlaBla");
        cur.setAccount(acc);
        personService.save(cur);

        Order o = new Order();
        o.setCourier(cur);
        o.setCustomer(cus);
        o.setStatus(OrderStatus.IN_PROGRESS);
        o = orderService.save(o);
        log.info("order: {}", o);

        o = orderService.save(o);
        log.info("new order: {}", o);


        acc = new Account();
        cus = new Customer();

        cus.setFirstName("Артем");
        cus.setLastName("Артемов");
        cus.setAccount(acc);


//        Customer customer = (Customer) personService.findById(4L).get();
//        Courier courier = (Courier) personService.findById(3L).get();
//        Order order = new Order();
//
//        order.setCourier(courier);
//        order.setCustomer(customer);
//        order.setStatus(OrderStatus.NEW);

        //orderService.save(order);


    }
}




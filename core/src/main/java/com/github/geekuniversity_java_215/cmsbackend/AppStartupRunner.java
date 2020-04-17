package com.github.geekuniversity_java_215.cmsbackend;

import com.github.geekuniversity_java_215.cmsbackend.entites.*;
import com.github.geekuniversity_java_215.cmsbackend.service.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.service.ItemService;
import com.github.geekuniversity_java_215.cmsbackend.service.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.service.PersonService;
import com.github.geekuniversity_java_215.cmsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private final AccountService accountService;
    private final PersonService personService;
    private final ItemService itemService;
    private final OrderService orderService;




    public AppStartupRunner(AccountService accountService, PersonService personService, ItemService itemService, OrderService orderService) {
        this.accountService = accountService;
        this.personService = personService;
        this.itemService = itemService;
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

        Account acc = new Account(123456789L);


        Customer cus = new Customer();
        cus.setFirstName("Вася");
        cus.setLastName("Пупкин");
        cus.setAccount(acc);

        acc = accountService.save(acc);
        log.info("customer id: {}", cus.getId());

        // should be the same
        acc = accountService.findById(1L).get();


        acc = new Account(987654321L);
        cus = new Customer();
        cus.setFirstName("Семен");
        cus.setLastName("Семенов");
        cus.setAccount(acc);
        personService.save(cus);

        acc = accountService.findById(2L).get();
        log.info("loaded: {} ok!", acc);



        acc = new Account(234234234L);
        Courier cur = new Courier();
        cur.setFirstName("Zed");
        cur.setLastName("Zed");
        cur.setBlaBla("BlaBla");
        cur.setAccount(acc);
        personService.save(cur);


        Item it1 = new Item("First Thing");
        it1 = itemService.save(it1);


        
        Item it2 = new Item("Second Thing");
        it2 = itemService.save(it2);

        Order o = new Order();
        o.setCourier(cur);
        o.setCustomer(cus);
        o.getItemList().add(it1);
        o.getItemList().add(it2);
        o = orderService.save(o);
        log.info("order: {}", o);


        o.getItemList().remove(1);
        o = orderService.save(o);
        log.info("new order: {}", o);
    }
}




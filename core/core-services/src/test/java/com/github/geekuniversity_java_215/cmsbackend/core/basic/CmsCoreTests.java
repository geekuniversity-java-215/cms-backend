package com.github.geekuniversity_java_215.cmsbackend.core.basic;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.services.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.pivovarit.function.ThrowingRunnable.unchecked;


@SpringBootTest
@Slf4j
@SuppressWarnings({"OptionalGetWithoutIsPresent"})
class CmsCoreTests {

    @Autowired
    ApplicationContext context;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private OrderService orderService;


    @Test
    @org.junit.jupiter.api.Order(-1000)
    void basicTest() {

        // TESTING LOG LEVELS
        log.info("TESTING LOG LEVELS");

        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        log.info("");

        // TESTING DB

        Account acc = new Account();
        User user = new User("vasya", "INVALID",
                "Залипов", "Вася", "vasya@mail.ru", "123");
        user.setAccount(acc);
        userService.save(user);
        log.info("user id: {}", user.getId());
        Courier courier = new Courier(user, "КУРЬЕР_DATA");
        courierService.save(courier);

        User finalUser = user;
        userService.findByFullName(user.getLastName(), user.getFirstName())
                .orElseThrow( () -> new UsernameNotFoundException(finalUser.getFullName() + " не найден"));

        acc = new Account();
        user = new User("sema", "INVALID",
                "Семенов", "Семен", "semen@mail.ru", "456");
        user.setAccount(acc);
        userService.save(user);
        Client client = new Client(user, "КЛИЕНТ_DATA");
        clientService.save(client);
        


        acc = accountService.findById(2L).get();
        log.info("loaded: {} ok!", acc);




        Order o = new Order();
        o.setCourier(courier);
        o.setClient(client);
        o.setStatus(OrderStatus.IN_PROGRESS);
        o = orderService.save(o);
        log.info("order: {}", o);

        o = orderService.save(o);
        log.info("new order: {}", o);

    }

    @Test
    void checkTableRowLock() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Account finalAcc1 = accountService.findById(1L).get();
        Account finalAcc2 = accountService.findById(1L).get();
        Future<?> f1 = executor.submit(unchecked(() ->
                accountService.addBalance(finalAcc1, BigDecimal.valueOf(100), CurrencyCode.RUB)));
        Future<?> f2 = executor.submit(unchecked(() ->
                accountService.addBalance(finalAcc2, BigDecimal.valueOf(100), CurrencyCode.RUB)));

        f1.get();
        f2.get();
        Account acc = accountService.findById(1L).get();

        log.info("баланс: {}", acc.getBalance());
    }
}

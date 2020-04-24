package com.github.geekuniversity_java_215.cmsbackend.core;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.PersonService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Customer;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.pivovarit.function.ThrowingRunnable.unchecked;


@SpringBootTest
//@ActiveProfiles("dev")
@Slf4j
@SuppressWarnings("ConstantConditions")
class CmsCoreTests {

    @Autowired
    ApplicationContext context;

    @Autowired
    private AccountService accountService;
    @Autowired
    private PersonService personService;
    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() throws Exception {

        // TESTING DB

        Account acc = new Account();
        Customer cus = new Customer();
        cus.setFirstName("Вася"); cus.setLastName("Пупкин"); cus.setEmail("m@m.ru"); cus.setPhoneNumber("123");
        cus.setAccount(acc);
        personService.save(cus);
        log.info("customer id: {}", cus.getId());

        acc = new Account();
        cus = new Customer();

        cus.setFirstName("Семен"); cus.setLastName("Семенов"); cus.setEmail("m@m.ru"); cus.setPhoneNumber("123");
        cus.setAccount(acc);
        personService.save(cus);

        acc = accountService.findById(2L).get();
        log.info("loaded: {} ok!", acc);


        acc = new Account();
        Courier cur = new Courier();
        cur.setFirstName("Zed"); cur.setLastName("Zed"); cur.setBlaBla("BlaBla");
        cur.setEmail("m@m.ru"); cur.setPhoneNumber("123");
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

        cus.setFirstName("Артем"); cus.setLastName("Артемов"); cus.setEmail("m@m.ru"); cus.setPhoneNumber("123");
        cus.setAccount(acc);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Account finalAcc1 = accountService.findById(1L).get();
        Account finalAcc2 = accountService.findById(1L).get();
        Future<?> f1 = executor.submit(unchecked(() -> accountService.addBalance(finalAcc1, BigDecimal.valueOf(100))));
        Future<?> f2 = executor.submit(unchecked(() -> accountService.addBalance(finalAcc2, BigDecimal.valueOf(100))));

        f1.get();
        f2.get();
        acc = accountService.findById(1L).get();

        log.info("баланс: {}", acc.getBalance());
    }

}
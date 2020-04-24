package com.github.geekuniversity_java_215.cmsbackend;

import com.github.geekuniversity_java_215.cmsbackend.entites.Account;
import com.github.geekuniversity_java_215.cmsbackend.entites.Courier;
import com.github.geekuniversity_java_215.cmsbackend.entites.Customer;
import com.github.geekuniversity_java_215.cmsbackend.entites.Order;
import com.github.geekuniversity_java_215.cmsbackend.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.services.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.CompletableTask;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.pivovarit.function.ThrowingRunnable.unchecked;


@SuppressWarnings("ConstantConditions")
@SpringBootTest
@Slf4j
class CmsBackendApplicationTests {

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

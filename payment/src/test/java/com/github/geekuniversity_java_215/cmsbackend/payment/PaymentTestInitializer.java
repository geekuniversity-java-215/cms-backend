package com.github.geekuniversity_java_215.cmsbackend.payment;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentTestInitializer implements ApplicationRunner {

    private UserService userService;

    @Autowired
    public PaymentTestInitializer(UserService userService) {
        this.userService = userService;
    }

    // will run before tests
    @Override
    public void run(ApplicationArguments args) {
        Account account = new Account();
        User user = new User("vasya", "INVALID",
                "Пупкин", "Вася", "cmsbackendgeek@gmail.com","123");
        user.setAccount(account);
        userService.save(user);
    }
}


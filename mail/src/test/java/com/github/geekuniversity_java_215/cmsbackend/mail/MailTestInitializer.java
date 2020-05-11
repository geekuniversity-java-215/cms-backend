package com.github.geekuniversity_java_215.cmsbackend.mail;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class MailTestInitializer implements ApplicationRunner {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public MailTestInitializer(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    // will run before tests
    @Override
    public void run(ApplicationArguments args) {

        Account account = new Account();
        User user = new User("vasya", "INVALID",
                "Пупкин", "Вася", "cmsbackendgeek@gmail.com","123");
        user.setAccount(account);
        userService.save(user);

        accountService.addBalance(user.getAccount(), BigDecimal.TEN, CurrencyCode.USD);
    }
}


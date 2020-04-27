package com.github.geekuniversity_java_215.cmsbackend.core;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Customer;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class CoreTestStartupRunner implements ApplicationRunner {

    private final PersonService personService;
    private final AccountService accountService;

    @Autowired
    public CoreTestStartupRunner(PersonService personService, AccountService accountService) {
        this.personService = personService;
        this.accountService = accountService;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Prepare database here
    }
}


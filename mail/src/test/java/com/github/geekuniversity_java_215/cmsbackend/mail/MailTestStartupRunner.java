package com.github.geekuniversity_java_215.cmsbackend.mail;

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
public class MailTestStartupRunner implements ApplicationRunner {

    private final PersonService personService;
    private final AccountService accountService;

    @Autowired
    public MailTestStartupRunner(PersonService personService, AccountService accountService) {
        this.personService = personService;
        this.accountService = accountService;
    }

    // will run before tests
    @Override
    public void run(ApplicationArguments args) {

        Account account = new Account();
        Customer customer = new Customer();
        customer.setFirstName("Вася"); customer.setLastName("Пупкин"); customer.setEmail("cmsbackendgeek@gmail.com"); customer.setPhoneNumber("123");
        customer.setAccount(account);
        personService.save(customer);

        accountService.addBalance(customer.getAccount(), BigDecimal.TEN);
    }
}


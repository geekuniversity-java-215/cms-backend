package com.github.geekuniversity_java_215.cmsbackend.cmsapplication;

import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.entities.TestEntity;
import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.repositories.TestRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.mail.services.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Component
@Slf4j
public class CmsAppStartupRunner implements ApplicationRunner {

    private final UserService userService;
    private final TestRepository testRepository;
    private final MailService mailService;

    @Autowired
    public CmsAppStartupRunner(UserService userService, TestRepository testRepository, MailService mailService) {
        this.userService = userService;
        this.testRepository = testRepository;
        this.mailService = mailService;
    }

    @Override
    public void run(ApplicationArguments args) {

        testRepository.save(new TestEntity("Вася test"));
        log.info("TestEntity saved");

        Account account = new Account();
        User user = new User("Вася", "Пупкин", "vasya@mail.ru", "1122334455");
        user.setAccount(account);
        userService.save(user);

        user = userService.findById(1L).get();
        log.info("Client: {}", user);
        
        //mailService.sendMessage("cmsbackendgeek@gmail.com", "Дарова", "Дарова чувачелло, как жизяя?");
    }
}


package com.github.geekuniversity_java_215.cmsbackend.cmsapplication;

import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.entities.TestEntity;
import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.repository.TestRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CmsAppStartupRunner implements ApplicationRunner {

    private final AccountService accountService;

    private final TestRepository testRepository;

    @Autowired
    public CmsAppStartupRunner(AccountService accountService, TestRepository testRepository) {
        this.accountService = accountService;
        this.testRepository = testRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        testRepository.save(new TestEntity("Вася test"));
        log.info("TestEntity saved");
    }
}


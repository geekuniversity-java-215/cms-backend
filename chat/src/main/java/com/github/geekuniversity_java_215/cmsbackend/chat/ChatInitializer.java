package com.github.geekuniversity_java_215.cmsbackend.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-1000)
@Slf4j
public class ChatInitializer implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {

        // Init here
        log.info("ChatAppDemoInitializer started!");
    }
}


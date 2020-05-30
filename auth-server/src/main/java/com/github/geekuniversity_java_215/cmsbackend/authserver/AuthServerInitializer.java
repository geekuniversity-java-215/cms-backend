package com.github.geekuniversity_java_215.cmsbackend.authserver;

import com.github.geekuniversity_java_215.cmsbackend.core.UsersInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-1000)
@Slf4j
public class AuthServerInitializer implements ApplicationRunner {

    private final UsersInitializer usersInitializer;

    public AuthServerInitializer(UsersInitializer usersInitializer) {
        this.usersInitializer = usersInitializer;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.debug("AuthServer initialize users");
        usersInitializer.initUsers();
    }

}

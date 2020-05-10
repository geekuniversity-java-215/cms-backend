package com.github.geekuniversity_java_215.cmsbackend.authserver;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.*;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthServerTestInitializer implements ApplicationRunner {

    private static boolean loaded = false;

    @Value("${auth-server.skip-test-initialization:false}")
    private boolean skipInitialization;

    @Override
    public void run(ApplicationArguments args) {

        if(skipInitialization) {
            return;
        }

        // Из-за того, что в тестах запускается auth-server целиком,
        // все ApplicationRunner вызываются по второму кругу
        // kludge-o-matic
        if (loaded) {
            return;
        }
        loaded = true;

        AuthServerApplication.main(new String[]{});
    }
}


package com.github.geekuniversity_java_215.cmsbackend.payment;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;


@Component
@Order(-1000)
@Slf4j
public class PaymentInitializer implements ApplicationRunner {

    private final Environment environment;

    public PaymentInitializer(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        // Port
//        log.info("port: {}", environment.getProperty("server.port"));
//        log.info("local port: {}", environment.getProperty("local.server.port"));
//        // Local address
//        log.info("localhost address: {}" , InetAddress.getLocalHost().getHostAddress());
//        log.info("localhost name: {}", InetAddress.getLocalHost().getHostName());
//
//        // Remote address
//        log.info("remotehost address: {}", InetAddress.getLoopbackAddress().getHostAddress());
//        log.info("remotehost name: {}", InetAddress.getLoopbackAddress().getHostName());
//
//        //serverProperties.setServerPort(environment.getProperty("local.server.port"));
    }

}

package com.github.geekuniversity_java_215.cmsbackend.authserver.controllers;

import com.github.geekuniversity_java_215.cmsbackend.authserver.AuthServerApplication;
import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.AuthServerTestSpringConfiguration;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.admin.AdminRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(classes = AuthServerApplication.class)
@Slf4j
class AdminControllerTest {

    @Autowired
    AdminRequest adminRequest;

    @Autowired
    AuthServerTestSpringConfiguration authServerTestSpringConfiguration;

//    @BeforeAll
//    private static void beforeAll() {
//    }

    @Test
    void test() {

        // Use here admin properties (principals and credentials)
        authServerTestSpringConfiguration.switchJrpcClientProperties(AuthServerTestSpringConfiguration.ADMIN);

        ResponseEntity<String> response = adminRequest.test();
        log.info(response.toString());
        Assert.assertEquals("HttpStatus.status not 200", HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("response body not 'hello world'", "hello world", response.getBody());
    }
}
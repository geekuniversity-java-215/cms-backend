package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.controllers;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.admin.AdminRequest;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Slf4j
@SpringBootTest
class AdminControllerTests {

    @Autowired
    AdminRequest adminRequest;

    @Autowired
    SystemTestSpringConfiguration userConfig;

//    @BeforeAll
//    private static void beforeAll() {
//    }

    @Test
    void test() {

        // Use here admin properties (principals and credentials)
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);

        ResponseEntity<String> response = adminRequest.test();
        log.info(response.toString());
        Assert.assertEquals("HttpStatus.status not 200", HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("response body not 'hello world'", "hello world", response.getBody());
    }
}
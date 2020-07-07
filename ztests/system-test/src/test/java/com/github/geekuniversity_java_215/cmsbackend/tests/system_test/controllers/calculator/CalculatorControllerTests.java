package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.controllers.calculator;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.admin.AdminRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.calculator.CalculatorRequest;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
public class CalculatorControllerTests {

    @Autowired
    CalculatorRequest calculatorRequest;

    @Autowired
    SystemTestSpringConfiguration userConfig;

    @Test
    void test() {

        // Use here admin properties (principals and credentials)
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);

        double result = calculatorRequest.add(1,2);
        log.info("result: {}", result);
        Assert.assertEquals("result not the same", 3, result, 1E-06);

        result = calculatorRequest.sub(2,2);
        log.info("result: {}", result);
        Assert.assertEquals("result not the same",0, result, 1E-06);


        List<Double> list = calculatorRequest.ssuper(Stream.of(10D, 20D).collect(Collectors.toList()));
        log.info("result: {}", list);
        Assert.assertEquals("result not the same", 34, list.get(0),1E-06);
        Assert.assertEquals("result not the same",30, list.get(1),1E-06);


        list = calculatorRequest.zoper(10,Stream.of(10D, 20D).collect(Collectors.toList()));
        log.info("result: {}", list);
        Assert.assertEquals("result not the same",10,  list.get(0),1E-06);
        Assert.assertEquals("result not the same", 30, list.get(1), 1E-06);
    }
}

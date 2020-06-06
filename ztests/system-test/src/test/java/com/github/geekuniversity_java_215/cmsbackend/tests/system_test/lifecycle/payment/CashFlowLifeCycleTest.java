package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.lifecycle.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.client.ClientRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.payment.CashFlowRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowDto;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class CashFlowLifeCycleTest {

    private SystemTestSpringConfiguration userConfig;
    private ClientRequest clientRequest;
    private CashFlowRequest cashFlowRequest;

    @Autowired
    public CashFlowLifeCycleTest(SystemTestSpringConfiguration userConfig, ClientRequest clientRequest, CashFlowRequest cashFlowRequest) {
        this.userConfig = userConfig;
        this.clientRequest = clientRequest;
        this.cashFlowRequest = cashFlowRequest;
    }

    @Test
    void cashFlowLifeCycle() throws JsonProcessingException {

        // CLIENT ----------------------------------------------------------------------------------
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.CLIENT);
        ClientDto client = clientRequest.getCurrent();

        List<CashFlowDto> cashFlowDtoList= cashFlowRequest.findByUserAndDate(null);
        log.info("CashFlowDtos = "+cashFlowDtoList.toString());



    }


//    void orderLifeCycle() throws JsonProcessingException {
//
//        AddressDto from;
//        AddressDto to;
//        OrderDto order;
//        Long orderId;
//
//        // CLIENT ----------------------------------------------------------------------------------
//        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.CLIENT);
//        ClientDto client = clientRequest.getCurrent();
}

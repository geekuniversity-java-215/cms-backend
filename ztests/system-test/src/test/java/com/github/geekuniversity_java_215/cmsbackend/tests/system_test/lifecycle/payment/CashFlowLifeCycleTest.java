package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.lifecycle.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.client.ClientRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.payment.CashFlowRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.user.UserRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowRequestDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Slf4j
public class CashFlowLifeCycleTest {

    private SystemTestSpringConfiguration userConfig;
    private ClientRequest clientRequest;
    private UserRequest userRequest;
    private CashFlowRequest cashFlowRequest;

    private CashFlowDto cashFlow;
    private CashFlowRequestDto cashFlowRequestDto;

    @Autowired
    public CashFlowLifeCycleTest(SystemTestSpringConfiguration userConfig, ClientRequest clientRequest, UserRequest userRequest, CashFlowRequest cashFlowRequest) {
        this.userConfig = userConfig;
        this.clientRequest = clientRequest;
        this.userRequest = userRequest;
        this.cashFlowRequest = cashFlowRequest;
    }

    @Test
    void cashFlowLifeCycle() throws JsonProcessingException {

        // USER ----------------------------------------------------------------------------------
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.CLIENT);
        UserDto user=userRequest.getCurrent();

        cashFlowRequestDto=new CashFlowRequestDto();
        cashFlowRequestDto.setAmount(new BigDecimal(100));
        cashFlowRequestDto.setPayPalEmail("1@1.ru");

        Long id=cashFlowRequest.save(cashFlowRequestDto);
        log.info("CashFlow id="+id);

        List<CashFlowDto> cashFlowDtoList= cashFlowRequest.findAll(null);
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

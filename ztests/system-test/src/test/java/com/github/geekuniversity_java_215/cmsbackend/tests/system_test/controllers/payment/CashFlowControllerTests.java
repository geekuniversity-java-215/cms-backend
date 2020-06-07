package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.controllers.payment;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.client.ClientRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.payment.CashFlowRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.user.UserRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Slf4j
public class CashFlowControllerTests {

    @Autowired
    private SystemTestSpringConfiguration userConfig;
    @Autowired
    private ClientRequest clientRequest;
    @Autowired
    private UserRequest userRequest;
    @Autowired
    private CashFlowRequest cashFlowRequest;

    @Test
    @Order(0)
    void saveAndFindAll() {

        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.CLIENT);
        UserDto user = userRequest.getCurrent();

        CashFlowDto cashFlow = new CashFlowDto(
            user,
            new BigDecimal(1),
            "1@1.ru",
            CurrencyCode.RUB);

        cashFlowRequest.save(cashFlow);

        List<CashFlowDto> list = cashFlowRequest.findAll(null);

        log.info("result: {}", list);
    }
}

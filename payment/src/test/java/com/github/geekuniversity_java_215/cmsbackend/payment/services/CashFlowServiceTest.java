package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import com.github.geekuniversity_java_215.cmsbackend.payment.specification.CashFlowSpecBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("OptionalGetWithoutIsPresent")
//@SpringJUnitConfig
@SpringBootTest
@Slf4j
class CashFlowServiceTest {

    @Autowired
    private CashFlowService cashFlowService;

    @Autowired
    private UserService userService;

    @Autowired
    CashFlowSpecBuilder cashFlowSpecBuilder;



    @Test
    void addRequestForFunds() {
        CashFlow cf = new CashFlow(
            userService.findByUsername("vasya").get(),
            "залупа+",
            new BigDecimal(1),
            "1@1.ru",
            CurrencyCode.RUB);

        cf = cashFlowService.save(cf);
        Assert.assertNotNull(cf);

    }

    @Test
    void findAllNoSuccess() {
        CashFlowSpecDto specDto = new CashFlowSpecDto();
        specDto.setSuccessful(false);
        Specification<CashFlow> spec = cashFlowSpecBuilder.build(specDto);
        List<CashFlow> cashFlowList = cashFlowService.findAll(spec);
        assertThat(cashFlowList.size()).isGreaterThan(0);
    }
}
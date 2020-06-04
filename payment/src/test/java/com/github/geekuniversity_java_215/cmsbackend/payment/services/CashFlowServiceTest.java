package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@SpringBootTest
@Slf4j
class CashFlowServiceTest {

    private CashFlowService cashFlowService;
    private UserService userService;

    @Autowired
    public CashFlowServiceTest(CashFlowService cashFlowService,UserService userService) {
        this.cashFlowService = cashFlowService;
        this.userService=userService;
    }

    @Test
    void addRequestForFunds() {
        CashFlow cf;
        cf=cashFlowService.addRequestForFunds(userService.findByUsername("vasya").get().getId(),new BigDecimal(1),"1@1.ru",CurrencyCode.codeOf(643));
        Assert.assertNotNull(cf);

    }

    @Test
    void findAllWithEmptyDateSuccess() {
        List<CashFlow> cashFlowList =cashFlowService.findAllWithEmptyDateSuccess();
        assertThat(cashFlowList.size()).isGreaterThan(0);
    }
}
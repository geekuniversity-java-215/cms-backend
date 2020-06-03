package com.github.geekuniversity_java_215.cmsbackend.payment.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.payment.converter.CashFlowConverter;
import com.github.geekuniversity_java_215.cmsbackend.payment.services.CashFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@JrpcController(HandlerName.payment.path_payment)
@Slf4j
public class PaymentJrpcController {

    private final CashFlowConverter cashFlowConverter;
    private final CashFlowService cashFlowService;
    private final UserService userService;

    @Autowired
    public PaymentJrpcController(CashFlowConverter cashFlowConverter, CashFlowService cashFlowService, UserService userService) {
        this.cashFlowConverter = cashFlowConverter;
        this.cashFlowService = cashFlowService;
        this.userService = userService;
    }

    @JrpcMethod(HandlerName.payment.requestForFunds)
    public void requestForFunds(JsonNode params) {
        String[] pair = cashFlowConverter.parseParams(params,2);
        cashFlowService.addRequestForFunds(userService.getCurrentUser(),
            new BigDecimal(pair[0]), pair[1], CurrencyCode.codeOf(643));
//        List<CashFlow> cfList=cashFlowService.findAllWithEmptyDateSuccess();
//        cfList.forEach(cashFlow -> System.out.println(cashFlow.getPayPalEmail()));
    }

    @JrpcMethod(HandlerName.payment.requestCashFlow)
    public JsonNode requestCashFlow(JsonNode params){
        String[] pair = cashFlowConverter.parseParams(params,2);
        JsonNode jsonNode = null;

        return jsonNode;
    }

}

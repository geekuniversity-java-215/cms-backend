package com.github.geekuniversity_java_215.cmsbackend.payment.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.converter.CashFlowConverter;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import com.github.geekuniversity_java_215.cmsbackend.payment.services.CashFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@JrpcController(HandlerName.payment.path)
@Slf4j
public class RequestForFundsController {

    private final CashFlowConverter cashFlowConverter;
    private final CashFlowService cashFlowService;
    private final UserService userService;

    @Autowired
    public RequestForFundsController(CashFlowConverter cashFlowConverter, CashFlowService cashFlowService, UserService userService) {
        this.cashFlowConverter = cashFlowConverter;
        this.cashFlowService = cashFlowService;
        this.userService = userService;
    }

    @JrpcMethod(HandlerName.payment.requestForFunds)
    public void requestForFunds(JsonNode params) {
        String[] pair = cashFlowConverter.doubleParams(params);
        cashFlowService.addRequestForFunds(userService.getCurrent().getId(),
            new BigDecimal(pair[0]), pair[1], CurrencyCode.RUB);
    }

    //НЕ РАБОТАЕТ ССУКА
    @JrpcMethod(HandlerName.payment.requestForCashFlows)
    public JsonNode requestForCashFlows(JsonNode params){
        //String[] pair=cashFlowConverter.parseParams(params,2);
        CashFlowSpecDto specDto=cashFlowConverter.toSpecDto(params);
        log.info("CashFlowSpecDto = "+specDto.toString());
        List<CashFlow> cashFlowList=cashFlowService.findAll(specDto);
        return null;
    }

}
//    OrderSpecDto specDto = converter.toSpecDto(params);
//    List<Order> orderList =  orderClientService.findAll(specDto);
//        return converter.toDtoListJson(orderList);
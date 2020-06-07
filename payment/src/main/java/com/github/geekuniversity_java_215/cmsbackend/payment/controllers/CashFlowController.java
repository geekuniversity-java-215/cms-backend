package com.github.geekuniversity_java_215.cmsbackend.payment.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.converter.CashFlowConverter;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import com.github.geekuniversity_java_215.cmsbackend.payment.services.CashFlowService;
import com.github.geekuniversity_java_215.cmsbackend.payment.specification.CashFlowSpecBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@JrpcController(HandlerName.payment.cashflow.path)
@Slf4j
public class CashFlowController {

    private final CashFlowConverter converter;
    private final CashFlowService cashFlowService;
    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public CashFlowController(CashFlowConverter converter, CashFlowService cashFlowService,
                              UserService userService, UserConverter userConverter) {
        this.converter = converter;
        this.cashFlowService = cashFlowService;
        this.userService = userService;
        this.userConverter = userConverter;
    }

    //НЕ РАБОТАЕТ ССУКА
    @JrpcMethod(HandlerName.payment.cashflow.findAll)
    public JsonNode findAll(JsonNode params) {

        CashFlowSpecDto specDto = converter.toSpecDto(params);
        // filter by user
        specDto = filterCashFlowByUser(specDto);
        // build spec
        Specification<CashFlow> spec = CashFlowSpecBuilder.build(specDto);
        // get result
        List<CashFlow> cashFlowList = cashFlowService.findAll(spec);
        return converter.toDtoListJson(cashFlowList);
    }


    @JrpcMethod(HandlerName.payment.cashflow.save)
    public JsonNode save(JsonNode params) {

        CashFlow cashFlow = converter.toEntity(params);
        // set current user
        cashFlow.setUser(userService.getCurrent());
        cashFlow = cashFlowService.save(cashFlow);
        return converter.toIdJson(cashFlow);
    }

    // =================================================================================================

    private CashFlowSpecDto filterCashFlowByUser(CashFlowSpecDto specDto) {
        CashFlowSpecDto result = specDto;
        if(result == null) {
            result = new CashFlowSpecDto();
        }
        UserDto userDto = userConverter.toDto(userService.getCurrent());
        result.setUser(userDto);
        return result;
    }





}
//    OrderSpecDto specDto = converter.toSpecDto(params);
//    List<Order> orderList =  orderClientService.findAll(specDto);
//        return converter.toDtoListJson(orderList);
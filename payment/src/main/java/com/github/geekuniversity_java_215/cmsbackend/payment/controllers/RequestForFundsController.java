package com.github.geekuniversity_java_215.cmsbackend.payment.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.payment.converter.TransactionConverter;
import com.github.geekuniversity_java_215.cmsbackend.payment.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@JrpcController(HandlerName.payment.path_payment)
@Slf4j
public class RequestForFundsController {

    private final TransactionConverter transactionConverter;
    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public RequestForFundsController(TransactionConverter transactionConverter, TransactionService transactionService, UserService userService) {
        this.transactionConverter = transactionConverter;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @JrpcMethod(HandlerName.payment.requestForFunds)
    public void requestForFunds(JsonNode params) {
        log.info("Запустил requestForFunds");
        String[] pair = transactionConverter.parseParams(params);
        User user=userService.getCurrentUser().get();
        transactionService.addRequestForFunds(user.getId()
                , new BigDecimal(pair[0]), "RUB");
    }

}

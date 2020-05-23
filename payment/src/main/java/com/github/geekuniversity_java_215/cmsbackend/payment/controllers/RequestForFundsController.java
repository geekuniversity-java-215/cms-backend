package com.github.geekuniversity_java_215.cmsbackend.payment.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.payment.converter.TransactionConverter;
import com.github.geekuniversity_java_215.cmsbackend.payment.services.PayPalMassPaymentsService;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.utils.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.utils.controllers.jrpc.JrpcMethod;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@JrpcController(HandlerName.requestForFunds)
public class RequestForFundsController {

    private final PayPalMassPaymentsService payPalMassPaymentsService;
    private final TransactionConverter transactionConverter;

    @Autowired
    public RequestForFundsController(PayPalMassPaymentsService payPalMassPaymentsService, TransactionConverter transactionConverter) {
        this.payPalMassPaymentsService = payPalMassPaymentsService;
        this.transactionConverter = transactionConverter;

    }

    @JrpcMethod(HandlerName.requestForFunds)
    public JsonNode requestForFunds(JsonNode params){

        Long userId= transactionConverter.getId(params);
        BigDecimal amount= transactionConverter.getAmount(params);
        //todo парсить параметры запроса
        //todo вызвать метод записывающий строчку в таблицу RequestForFunds на вывод средств
        return

    }
}

package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.utils.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.utils.controllers.jrpc.JrpcMethod;
import org.springframework.beans.factory.annotation.Autowired;

@JrpcController(HandlerName.order.path)
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter converter;

    @Autowired
    public OrderController(OrderService orderService, OrderConverter converter) {
        this.orderService = orderService;
        this.converter = converter;
    }

    @JrpcMethod(HandlerName.order.findById)
    public JsonNode findById(JsonNode params) {

        Long id = converter.getId(params);
        Order order = orderService.findById(id).orElse(null);
        return converter.toDtoJson(order);
    }

}

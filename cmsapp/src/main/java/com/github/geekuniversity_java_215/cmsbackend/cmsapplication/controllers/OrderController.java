package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.order.OrderSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

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


    @JrpcMethod(HandlerName.order.findAllById)
    public JsonNode findAllById(JsonNode params) {

        List<Long> idList = converter.getIdList(params);
        List<Order> list = orderService.findAllById(idList);
        return converter.toDtoListJson(list);
    }

    @JrpcMethod(HandlerName.order.findAll)
    public JsonNode findAll(JsonNode params) {

        Optional<OrderSpecDto> specDto = converter.toSpecDto(params);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto.orElse(null));
        return converter.toDtoListJson(orderService.findAll(spec));
    }

    //
    //  Return first ProductSpecDto.limit elements
    //
    @JrpcMethod(HandlerName.order.findFirst)
    public JsonNode findFirst(JsonNode params) {

        Optional<OrderSpecDto> specDto = converter.toSpecDto(params);
        Specification<Order> spec = OrderSpecBuilder.build(specDto.orElse(null));
        int limit = specDto.map(OrderSpecDto::getLimit).orElse(1);
        Page<Order> page = orderService.findAll(spec, PageRequest.of(0, limit));
        return converter.toDtoListJson(page.toList());
    }


    @JrpcMethod(HandlerName.order.save)
    public JsonNode save(JsonNode params) {

        Order order = converter.toEntity(params);
        order = orderService.save(order);
        return converter.toIdJson(order);
    }

    @JrpcMethod(HandlerName.order.delete)
    public JsonNode delete(JsonNode params) {
        
        Order order = converter.toEntity(params);
        orderService.delete(order);
        return null;
    }

}

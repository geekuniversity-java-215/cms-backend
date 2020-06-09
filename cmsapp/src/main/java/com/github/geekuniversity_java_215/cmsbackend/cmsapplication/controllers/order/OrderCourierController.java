package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.services.order.OrderCourierService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@JrpcController(HandlerName.order.courier.path)
@Secured(UserRole.VAL.COURIER)
public class OrderCourierController {

    private final OrderConverter converter;
    private final OrderCourierService orderCourierService;

    @Autowired
    public OrderCourierController(OrderConverter converter, OrderCourierService orderCourierService) {
        this.converter = converter;
        this.orderCourierService = orderCourierService;
    }



    /**
     * Get Order by id, filtered by orders belonged only to current courier
     * @param params Long id
     * @return OrderDto
     */
    @JrpcMethod(HandlerName.order.courier.findById)
    public JsonNode findById(JsonNode params) {

        Long id = converter.get(params, Long.class);
        Order order = orderCourierService.findById(id).orElse(null);
        return converter.toDtoJson(order);
    }


    /**
     * Get all my Orders, filtered by orders belonged only to current courier
     * @param params OrderSpecDto/null
     * @return {@code List<OrderDto>}
     */
    @JrpcMethod(HandlerName.order.courier.findAll)
    public JsonNode findAll(JsonNode params) {

        OrderSpecDto specDto = converter.toSpecDto(params);
        List<Order> orderList =  orderCourierService.findAll(specDto);
        return converter.toDtoListJson(orderList);
    }


    /**
     * Get all Orders, that have status == NEW
     * @param params OrderSpecDto/null
     * @return {@code List<OrderDto>}
     */
    @JrpcMethod(HandlerName.order.courier.findNew)
    public JsonNode findNew(JsonNode params) {

        OrderSpecDto specDto = converter.toSpecDto(params);
        List<Order> orderList =  orderCourierService.findNew(specDto);
        return converter.toDtoListJson(orderList);
    }


    /**
     * Accept Order. Order should have status == NEW
     * @param params Long orderId
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.courier.accept)
    public JsonNode accept(JsonNode params) {

        Long orderId = converter.get(params, Long.class);
        Order order = orderCourierService.accept(orderId);
        return converter.toIdJson(order);
    }

    /**
     * Execute Order. Order should have status == ASSIGNED
     * @param params Long orderId
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.courier.execute)
    public JsonNode execute(JsonNode params) {

        Long orderId = converter.get(params, Long.class);
        Order order = orderCourierService.execute(orderId);
        return converter.toIdJson(order);
    }


    /**
     * Complete Order. Order should have status == TRANSIT
     * @param params Long orderId
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.courier.complete)
    public JsonNode complete(JsonNode params) {

        Long orderId = converter.get(params, Long.class);
        Order order = orderCourierService.complete(orderId);
        return converter.toIdJson(order);
    }


    /**
     * Close Order. Order should have status == COMPLETED
     * @param params Long orderId
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.courier.close)
    public JsonNode close(JsonNode params) {

        Long orderId = converter.get(params, Long.class);
        Order order = orderCourierService.close(orderId);
        return converter.toIdJson(order);
    }
}

//@SuppressWarnings("DuplicatedCode")

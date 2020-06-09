package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.services.order.OrderClientService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
import org.springframework.security.access.annotation.Secured;
import java.util.List;


@JrpcController(HandlerName.order.client.path)
@Secured(UserRole.VAL.CLIENT)
public class OrderClientController {



    private final OrderConverter converter;
    private final OrderClientService orderClientService;

    public OrderClientController(OrderConverter converter, OrderClientService orderClientService) {
        this.converter = converter;
        this.orderClientService = orderClientService;
    }

    /**
     * Get Order by id, filtered by orders belonged only to current client
     * @param params Long id
     * @return OrderDto
     */
    @JrpcMethod(HandlerName.order.client.findById)
    public JsonNode findById(JsonNode params) {

        Long id = converter.get(params, Long.class);
        Order order = orderClientService.findById(id).orElse(null);
        return converter.toDtoJson(order);
    }


    /**
     * Get all my Orders, filtered by orders belonged only to current client
     * @param params OrderSpecDto/null
     * @return {@code List<OrderDto>}
     */
    @JrpcMethod(HandlerName.order.client.findAll)
    public JsonNode findAll(JsonNode params) {

        OrderSpecDto specDto = converter.toSpecDto(params);
        List<Order> orderList =  orderClientService.findAll(specDto);
        return converter.toDtoListJson(orderList);
    }


    /**
     * Save new created Order
     * @param params OrderDto
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.client.save)
    public JsonNode save(JsonNode params) {

        Order order = converter.toEntity(params);
        order = orderClientService.save(order);
        return converter.toIdJson(order);
    }


    /**
     * Delete order if its status == NEW
     * @param params Long orderId
     * @return Long deleted orderId
     */
    @JrpcMethod(HandlerName.order.client.cancel)
    public JsonNode cancel(JsonNode params) {

        Long id = converter.get(params, Long.class);
        Order order = orderClientService.cancel(id);
        return converter.toIdJson(order);
    }


    // ==================================================================================


}

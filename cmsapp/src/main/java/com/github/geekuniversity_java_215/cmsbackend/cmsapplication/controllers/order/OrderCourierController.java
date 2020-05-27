package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.order.OrderSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;

import java.util.Optional;

@JrpcController(HandlerName.order.courier.path)
@Secured(UserRole.COURIER)
public class OrderCourierController {

    private final OrderService orderService;
    private final OrderConverter converter;

    @Autowired
    public OrderCourierController(OrderService orderService, OrderConverter converter) {
        this.orderService = orderService;
        this.converter = converter;
    }

    // FixMe: будем тупо проверять, принадлежит ли Courier order, с которым он хочет работать


    @JrpcMethod(HandlerName.order.courier.findAll)
    public JsonNode findAll(JsonNode params) {

        Optional<OrderSpecDto> specDto = converter.toSpecDto(params);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto.orElse(null));
        return converter.toDtoListJson(orderService.findAll(spec));
    }



    // ==================================================================================


    private void checkIsMyOrder(Order order) {

    }

}

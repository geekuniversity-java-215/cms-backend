package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.courier.CourierConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.order.OrderSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;

import java.util.concurrent.atomic.AtomicReference;

@JrpcController(HandlerName.order.courier.path)
@Secured(UserRole.COURIER)
public class OrderCourierController {

    private final OrderService orderService;
    private final OrderConverter converter;
    private final CourierConverter courierConverter;

    private final UserService userService;
    private final CourierService courierService;

    @Autowired
    public OrderCourierController(OrderService orderService, OrderConverter converter,
                                  CourierConverter courierConverter, UserService userService, CourierService courierService) {
        this.orderService = orderService;
        this.converter = converter;
        this.courierConverter = courierConverter;
        this.userService = userService;
        this.courierService = courierService;
    }

    // Will not show new Orders, only all of mine orders
    @JrpcMethod(HandlerName.order.courier.findAll)
    public JsonNode findAll(JsonNode params) {

        OrderSpecDto specDto = filterOrderShowOnlyMine(params);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto);
        return converter.toDtoListJson(orderService.findAll(spec));
    }


    @JrpcMethod(HandlerName.order.courier.findNew)
    public JsonNode findNew(JsonNode params) {

        OrderSpecDto specDto = filterOrderShowOnlyNew(params);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto);
        return converter.toDtoListJson(orderService.findAll(spec));
    }


    @JrpcMethod(HandlerName.order.courier.accept)
    public JsonNode accept(JsonNode params) {

        long orderId = converter.get(params, Long.class);

        //ToDo: move to service with transaction

        Order order = orderService.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if(order.getStatus() != OrderStatus.NEW) {
            throw new IllegalArgumentException("Order not in NEW status");
        }

        order.setCourier(getCurrentCourier());
        order.setStatus(OrderStatus.ASSIGNED);
        order = orderService.save(order);

        return converter.toIdJson(order);
    }

    @JrpcMethod(HandlerName.order.courier.execute)
    public JsonNode execute(JsonNode params) {

        long orderId = converter.get(params, Long.class);

        //ToDo: move to service with transaction

        Order order = orderService.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if(order.getStatus() != OrderStatus.ASSIGNED) {
            throw new IllegalArgumentException("Order not in ASSIGNED status");
        }

        order.setStatus(OrderStatus.IN_PROGRESS);
        order = orderService.save(order);
        return converter.toIdJson(order);
    }


    @JrpcMethod(HandlerName.order.courier.complete)
    public JsonNode complete(JsonNode params) {

        long orderId = converter.get(params, Long.class);

        //ToDo: move to service with transaction

        Order order = orderService.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if(order.getStatus() != OrderStatus.IN_PROGRESS) {
            throw new IllegalArgumentException("Order not in NEW status");
        }

        order.setStatus(OrderStatus.DONE);
        order = orderService.save(order);
        return converter.toIdJson(order);
    }






    // ==================================================================================

    private Courier getCurrentCourier() {

        AtomicReference<Courier> result = new AtomicReference<>();
        courierService.findOneByUser(userService.getCurrentUser()).ifPresent(result::set);
        return result.get();
    }

    private OrderSpecDto filterOrderShowOnlyMine(JsonNode params) {

        OrderSpecDto result;
        AtomicReference<OrderSpecDto> specDtoAtom = new AtomicReference<>();
        converter.toSpecDto(params).ifPresent(specDtoAtom::set);
        result = specDtoAtom.get();

        // assign current courier to OrderSpecDto
        if(result == null) {
            result = new OrderSpecDto();
        }
        CourierDto courierDto = courierConverter.toDto(getCurrentCourier());
        result.setCourier(courierDto);

        return result;
    }


    private OrderSpecDto filterOrderShowOnlyNew(JsonNode params) {

        OrderSpecDto result;
        AtomicReference<OrderSpecDto> specDtoAtom = new AtomicReference<>();
        converter.toSpecDto(params).ifPresent(specDtoAtom::set);
        result = specDtoAtom.get();

        // assign current courier to OrderSpecDto
        if(result == null) {
            result = new OrderSpecDto();
        }
        result.setStatus(OrderStatusDto.NEW);

        return result;
    }

}

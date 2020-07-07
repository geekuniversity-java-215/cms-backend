package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.order;


import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.services.order.OrderCourierService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderDto;
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
    public OrderDto findById(Long id) {

        Order order = orderCourierService.findById(id).orElse(null);
        return converter.toDto(order);
    }


    /**
     * Get all my Orders, filtered by orders belonged only to current courier
     * @param params OrderSpecDto/null
     * @return {@code List<OrderDto>}
     */
    @JrpcMethod(HandlerName.order.courier.findAll)
    public List<OrderDto> findAll(OrderSpecDto specDto) {

        List<Order> orderList =  orderCourierService.findAll(specDto);
        return converter.toDtoList(orderList);
    }


    /**
     * Get all Orders, that have status == NEW
     * @param params OrderSpecDto/null
     * @return {@code List<OrderDto>}
     */
    @JrpcMethod(HandlerName.order.courier.findNew)
    public List<OrderDto> findNew(OrderSpecDto specDto) {

        List<Order> orderList =  orderCourierService.findNew(specDto);
        return converter.toDtoList(orderList);
    }


    /**
     * Accept Order. Order should have status == NEW
     * @param params Long orderId
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.courier.accept)
    public Long accept(Long orderId) {

        Order order = orderCourierService.accept(orderId);
        return order.getId();
    }

    /**
     * Execute Order. Order should have status == ASSIGNED
     * @param params Long orderId
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.courier.execute)
    public Long execute(Long orderId) {

        Order order = orderCourierService.execute(orderId);
        return order.getId();
    }


    /**
     * Complete Order. Order should have status == TRANSIT
     * @param params Long orderId
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.courier.complete)
    public Long complete(Long orderId) {

        Order order = orderCourierService.complete(orderId);
        return order.getId();
    }


    /**
     * Close Order. Order should have status == COMPLETED
     * @param params Long orderId
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.courier.close)
    public Long close(Long orderId) {

        Order order = orderCourierService.close(orderId);
        return order.getId();
    }
}

//@SuppressWarnings("DuplicatedCode")

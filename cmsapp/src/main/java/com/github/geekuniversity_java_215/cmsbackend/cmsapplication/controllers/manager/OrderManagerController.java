package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.order.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.order.OrderSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@JrpcController(HandlerName.manager.order.path)
@Secured(UserRole.MANAGER)
public class OrderManagerController {

    private final OrderService orderService;
    private final OrderConverter converter;

    @Autowired
    public OrderManagerController(OrderService orderService, OrderConverter converter) {
        this.orderService = orderService;
        this.converter = converter;
    }

    /**
     * Get Order by id
     * @param params Long id
     * @return
     */
    @JrpcMethod(HandlerName.manager.order.findById)
    public JsonNode findById(JsonNode params) {

        Long id = converter.get(params, Long.class);
        Order order = orderService.findById(id).orElse(null);
        return converter.toDtoJson(order);
    }


    /**
     * Get List<Order> by idList
     * @param params List<Long> idList
     * @return
     */
    @JrpcMethod(HandlerName.manager.order.findAllById)
    public JsonNode findAllById(JsonNode params) {

        List<Long> idList = converter.getList(params, Long.class);
        List<Order> list = orderService.findAllById(idList);
        return converter.toDtoListJson(list);
    }

    /**
     * Get order by Specification
     * @param params OrderSpecDto
     * @return
     */
    @JrpcMethod(HandlerName.manager.order.findAll)
    public JsonNode findAll(JsonNode params) {

        OrderSpecDto specDto = converter.toSpecDto(params);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto);
        return converter.toDtoListJson(orderService.findAll(spec));
    }

    //
    //  Return first ProductSpecDto.limit elements
    //

    /***
     * Get first limit elements by OrderSpecDto (with ~pagination)
     * <br> So, for example you got first 10 Orders, ordered by price.
     * Then set filter price greater than price in 10 Order and ask (next) 10 Orders and so on
     * Until you got no moar orderz
     * @param params
     * @return
     */
    @JrpcMethod(HandlerName.manager.order.findFirst)
    public JsonNode findFirst(JsonNode params) {

        OrderSpecDto specDto = converter.toSpecDto(params);
        int limit = specDto != null ? specDto.getLimit() : 1;
        Specification<Order> spec = OrderSpecBuilder.build(specDto);
        Page<Order> page = orderService.findAll(spec, PageRequest.of(0, limit));
        return converter.toDtoListJson(page.toList());
    }


    /**
     * Save Order(insert new or update existing)
     * @param params Order
     * @return
     */
    @JrpcMethod(HandlerName.manager.order.save)
    public JsonNode save(JsonNode params) {

        Order order = converter.toEntity(params);
        order = orderService.save(order);
        return converter.toIdJson(order);
    }
    

    /**
     * Delete Order
     * @param params
     * @return
     */
    @JrpcMethod(HandlerName.manager.order.delete)
    public JsonNode delete(JsonNode params) {
        
        Order order = converter.toEntity(params);
        orderService.delete(order);
        return null;
    }
}

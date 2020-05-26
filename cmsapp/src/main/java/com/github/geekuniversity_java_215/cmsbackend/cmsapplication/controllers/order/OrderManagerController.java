package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
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
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Optional;

@JrpcController(HandlerName.order_manager.path)
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
    @JrpcMethod(HandlerName.order_manager.findById)
    public JsonNode findById(JsonNode params) {

        Long id = converter.getId(params);
        Order order = orderService.findById(id).orElse(null);
        return converter.toDtoJson(order);
    }


    /**
     * Get List<Order> by idList
     * @param params List<Long> idList
     * @return
     */
    @JrpcMethod(HandlerName.order_manager.findAllById)
    public JsonNode findAllById(JsonNode params) {

        List<Long> idList = converter.getIdList(params);
        List<Order> list = orderService.findAllById(idList);
        return converter.toDtoListJson(list);
    }

    /**
     * Get order by Specification
     * @param params OrderSpecDto
     * @return
     */
    @JrpcMethod(HandlerName.order_manager.findAll)
    public JsonNode findAll(JsonNode params) {

        Optional<OrderSpecDto> specDto = converter.toSpecDto(params);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto.orElse(null));
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
    @JrpcMethod(HandlerName.order_manager.findFirst)
    public JsonNode findFirst(JsonNode params) {

        Optional<OrderSpecDto> specDto = converter.toSpecDto(params);
        Specification<Order> spec = OrderSpecBuilder.build(specDto.orElse(null));
        int limit = specDto.map(OrderSpecDto::getLimit).orElse(1);
        Page<Order> page = orderService.findAll(spec, PageRequest.of(0, limit));
        return converter.toDtoListJson(page.toList());
    }


    /**
     * Save Order(insert new or update existing)
     * @param params Order
     * @return
     */
    @JrpcMethod(HandlerName.order_manager.save)
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
    @JrpcMethod(HandlerName.order_manager.delete)
    public JsonNode delete(JsonNode params) {
        
        Order order = converter.toEntity(params);
        orderService.delete(order);
        return null;
    }

    // COURIER specific ===============================================

}

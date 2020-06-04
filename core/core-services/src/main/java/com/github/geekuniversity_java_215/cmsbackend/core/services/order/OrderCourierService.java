package com.github.geekuniversity_java_215.cmsbackend.core.services.order;

import com.github.geekuniversity_java_215.cmsbackend.core.converters.courier.CourierConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.order.OrderSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderStatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class OrderCourierService {

    private final OrderService orderService;
    private final CourierService courierService;
    private final CourierConverter courierConverter;

    public OrderCourierService(OrderService orderService, CourierService courierService, CourierConverter courierConverter) {
        this.orderService = orderService;
        this.courierService = courierService;
        this.courierConverter = courierConverter;
    }


    public Optional<Order> findById(Long id) {

        OrderSpecDto specDto = filterOrderShowOnlyMine(null);
        specDto.setId(id);
        Specification<Order> spec = OrderSpecBuilder.build(specDto);
        return orderService.findOne(spec);
    }

    public List<Order> findAll(OrderSpecDto specDto) {

        specDto = filterOrderShowOnlyMine(specDto);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto);
        return orderService.findAll(spec);
    }

    public List<Order> findNew(OrderSpecDto specDto) {

        specDto = specDto == null ?  new OrderSpecDto() : specDto;
        specDto.setStatus(OrderStatusDto.NEW);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto);
        return orderService.findAll(spec);
    }


    public Order accept(Long id) {

        Order order = orderService.findByIdOrError(id);
        order.setCourier(courierService.getCurrent());
        order.setStatus(OrderStatus.ASSIGNED);
        // will check that previous version of Order.status == NEW
        return orderService.save(order);
    }

    public Order execute(Long id) {

        Order order = orderService.findByIdOrError(id);
        checkOrderOwner(order);
        order.setStatus(OrderStatus.TRANSIT);
        return orderService.save(order);
    }

    public Order complete(Long id) {

        Order order = orderService.findByIdOrError(id);
        checkOrderOwner(order);
        order.setStatus(OrderStatus.COMPLETED);
        return orderService.save(order);
    }

    public Order close(Long id) {

        Order order = orderService.findByIdOrError(id);
        checkOrderOwner(order);
        order.setStatus(OrderStatus.CLOSED);
        return orderService.save(order);
    }



    // =================================================================================



    private OrderSpecDto filterOrderShowOnlyMine(OrderSpecDto specDto) {

        OrderSpecDto result = specDto;
        // assign current courier to OrderSpecDto
        if(result == null) {
            result = new OrderSpecDto();
        }
        CourierDto courierDto = courierConverter.toDto(courierService.getCurrent());
        result.setCourier(courierDto);
        return result;
    }



    /**
     * Check that currently being modified Order is belong to current Courier
     * @param order
     */
    private void checkOrderOwner(Order order) {

        if(order != null && order.getId() != null) {
            Order old = orderService.findById(order.getId()).orElseThrow(() ->
                new IllegalArgumentException("Order with id:\n" + order.getId() + " not exists"));

            if (!old.getCourier().equals(courierService.getCurrent())) {
                throw new AccessDeniedException("HAAKCEEER !!!");
            }
        }
    }

//    private void checkOrderOwner(Long id) {
//
//        Order order = findById(id)
//            .orElseThrow(() -> new IllegalArgumentException("Order " + id + " not found"));
//        checkOrderOwner(order);
//    }


}

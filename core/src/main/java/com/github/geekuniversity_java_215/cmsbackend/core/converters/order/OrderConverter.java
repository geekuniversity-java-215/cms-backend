package com.github.geekuniversity_java_215.cmsbackend.core.converters.order;

import com.github.geekuniversity_java_215.cmsbackend.core.converters.base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.order.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter extends AbstractConverter<Order> {

    private final OrderMapper orderMapper;

    @Autowired
    OrderConverter(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    
    public OrderDto toDto(Order order) {

        OrderDto result = null;

        // ToDo: implement mapstruct logic here
        return result;
    }

    public Order toEntity(OrderDto orderDto) {

        Order result = null;

        // ToDo: implement mapstruct logic here
        return result;
    }

}

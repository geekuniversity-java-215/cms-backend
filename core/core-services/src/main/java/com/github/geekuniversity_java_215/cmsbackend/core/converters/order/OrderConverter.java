package com.github.geekuniversity_java_215.cmsbackend.core.converters.order;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter extends AbstractConverter<Order, OrderDto, OrderSpecDto> {

    @Autowired
    public OrderConverter(OrderMapper orderMapper) {
        this.entityMapper = orderMapper;

        this.entityClass = Order.class;
        this.dtoClass = OrderDto.class;
        this.specClass = OrderSpecDto.class;
    }

    @Override
    protected void validate(Order order) {
        super.validate(order);

        // ... custom Order validation
    }
}

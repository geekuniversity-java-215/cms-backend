package com.github.geekuniversity_java_215.cmsbackend.core.converters.order;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.order.OrderDto;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter extends AbstractConverter<Order, OrderDto, Void> {

    @Autowired
    public OrderConverter(OrderMapper orderMapper) {
        this.entityMapper = orderMapper;

        this.entityClass = Order.class;
        this.dtoClass = OrderDto.class;
        this.specClass = Void.class;
    }

    @Override
    protected void validate(Order order) {
        super.validate(order);

        // ... custom validation
    }
}

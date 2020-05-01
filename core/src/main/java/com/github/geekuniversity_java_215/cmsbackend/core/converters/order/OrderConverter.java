package com.github.geekuniversity_java_215.cmsbackend.core.converters.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.order.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
public class OrderConverter extends AbstractConverter<Order> {

    private final OrderMapper orderMapper;

    @Autowired
    OrderConverter(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }


    // Json => Dto => Entity
    public Order toEntity(JsonNode params)  {
        try {
            OrderDto dto = objectMapper.treeToValue(params, OrderDto.class);
            Order result = orderMapper.toEntity(dto);
            validate(result);
            return result;
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ParseException(0, "OrderDto parse error", e);
        }
    }


    // Entity => Dto => Json
    public JsonNode toDtoJson(Order order) {
        try {
            OrderDto dto = orderMapper.toDto(order);
            return objectMapper.valueToTree(dto);
        }
        catch (Exception e) {
            throw new ParseException(0, "OrderToDtoJson convert error", e);
        }
    }

    @Override
    protected void validate(Order order) {
        super.validate(order);

        // ... custom validation
    }
}

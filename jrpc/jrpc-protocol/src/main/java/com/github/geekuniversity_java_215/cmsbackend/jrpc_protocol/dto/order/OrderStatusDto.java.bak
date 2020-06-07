package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
//@ToString(exclude = "inWork")
public enum OrderStatusDto {

    NULL(0), // kludge
    NEW(1),
    ASSIGNED(2),
    TRANSIT(3),
    CANCELLED(4),
    MODERATION(5),
    COMPLETED(6),
    CLOSED(7);

    private final static Map<Integer, OrderStatusDto> ENUM_MAP = new HashMap<>();

    static {
        for(OrderStatusDto item : OrderStatusDto.values()) {
            if (ENUM_MAP.containsKey(item.id)){
                throw new RuntimeException("OrderStatus duplicate id");
            }
            ENUM_MAP.put(item.id, item);
        }
    }

    private final int id;
    
    public static OrderStatusDto getById(int id) {

        OrderStatusDto result = ENUM_MAP.get(id);
        if (result == null) {
            throw new IllegalArgumentException("OrderStatus - no matching constant for [" + id + "]");
        }
        return result;
    }
}

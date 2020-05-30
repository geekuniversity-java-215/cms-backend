package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
//@ToString(exclude = "inWork")
public enum OrderStatusDto {

    NULL(-1), // kludge
    NEW(0),
    ASSIGNED(1),
    IN_PROGRESS(2),
    CANCEL(3),
    IN_MODERATION(4),
    DONE(5);

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

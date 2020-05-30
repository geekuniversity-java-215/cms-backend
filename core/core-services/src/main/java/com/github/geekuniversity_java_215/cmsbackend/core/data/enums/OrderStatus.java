package com.github.geekuniversity_java_215.cmsbackend.core.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
//@ToString(exclude = "inWork")
public enum OrderStatus {

    NULL(-1), // kludge
    NEW(0),
    ASSIGNED(1),
    IN_PROGRESS(2),
    CANCEL(3),
    IN_MODERATION(4),
    DONE(5);

    private final static Map<Integer, OrderStatus> ENUM_MAP = new HashMap<>();

    static {
        for(OrderStatus item : OrderStatus.values()) {
            if (ENUM_MAP.containsKey(item.id)){
                throw new RuntimeException("OrderStatus duplicate id");
            }
            ENUM_MAP.put(item.id, item);
        }
    }

    private final int id;
    
    public static OrderStatus getById(int id) {

        OrderStatus result = ENUM_MAP.get(id);
        if (result == null) {
            throw new IllegalArgumentException("OrderStatus - no matching constant for [" + id + "]");
        }
        return result;
    }

    //ToDo: разобраться с жизненным циклом заказа
    // пример
    // https://github.com/dreamworkerln/geekbrains_spring1/blob/master/lesson_02/jsonrpc/resource-server/src/main/java/jsonrpc/resourceserver/service/order/OrderService.java#L83
    // сделать матрицу решений, вместо .ordinal() использовать

    /*
     private final boolean inWork;
     public LinkedList<OrderStatus> getStatusInWork(){
        return getOrderByInWork(true);
    }

    public LinkedList<OrderStatus> getStatusFinished(){
        return getOrderByInWork(false);

    }

    private LinkedList<OrderStatus> getOrderByInWork(boolean inWork){

        LinkedList<OrderStatus> workList;
        workList = new LinkedList<>();

        for (OrderStatus orderStatus : values()){
            if (orderStatus.inWork == inWork) workList.add(orderStatus);
        }

        return workList;

    }*/


}

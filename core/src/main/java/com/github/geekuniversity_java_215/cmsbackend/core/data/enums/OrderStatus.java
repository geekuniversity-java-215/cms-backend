package com.github.geekuniversity_java_215.cmsbackend.core.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
@ToString(exclude = "inWork")
public enum OrderStatus {

    NEW(0, true),
    DONE(1,false),
    IN_PROGRESS(2,true),
    CANCEL(3,false),
    IN_MODERATION(4,true);

    private final int id;
    private final boolean inWork;


    public boolean equals(OrderStatus orderStatus){

        if (Objects.nonNull(orderStatus)) {
            return this.getId() == orderStatus.getId();
        }

        return false;

    }

    public static Set<OrderStatus> getStatusInWork(){
        return getOrderByInWork(true);
    }

    public static Set<OrderStatus> getStatusFinished(){
        return getOrderByInWork(false);
    }

    private static Set<OrderStatus> getOrderByInWork(boolean inWork){
        return Arrays.stream(values()).filter(e -> e.inWork == inWork).collect(Collectors.toSet());
    }


}

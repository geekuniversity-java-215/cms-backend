package com.github.geekuniversity_java_215.cmsbackend.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Objects;

@AllArgsConstructor
@Getter
@ToString(exclude = "inWork")
public enum OrderStatus {

    NEW(0, true),
    DONE(0,false),
    IN_PROGRESS(0,true),
    CANCEL(0,false),
    IN_MODERATION(0,true);

    private final int id;
    private final boolean inWork;


    public boolean equals(OrderStatus orderStatus){

        if (Objects.nonNull(orderStatus)) {
            return this.getId() == orderStatus.getId();
        }

        return false;

    }

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

    }


}

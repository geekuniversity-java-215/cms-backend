package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;

import javax.persistence.*;

@Entity
@Table(name = "courier")
public class Courier extends Person {

//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String CourierCourierCourier;

    public Courier(){}

    public String getCourierCourierCourier() {
        return CourierCourierCourier;
    }

    public void setCourierCourierCourier(String courierCourierCourier) {
        CourierCourierCourier = courierCourierCourier;
    }
}

// TODO: 09.04.2020 Добавить сущность автомобиль и подвязать к курьеру.

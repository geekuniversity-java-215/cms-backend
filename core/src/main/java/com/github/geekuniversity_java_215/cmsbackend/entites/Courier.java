package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;

import javax.persistence.*;

@Entity
@Table(name = "courier")
public class Courier extends Person {

    public Courier(){}

}

// TODO: 09.04.2020 Добавить сущность автомобиль и подвязать к курьеру.

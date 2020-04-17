package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courier")
public class Courier extends Person {

    private String blaBla;

    public Courier(){}

    public String getBlaBla() {
        return blaBla;
    }

    public void setBlaBla(String blaBla) {
        this.blaBla = blaBla;
    }
}

// TODO: 09.04.2020 Добавить сущность автомобиль и подвязать к курьеру.

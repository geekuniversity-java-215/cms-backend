package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "courier")
@Data
@EqualsAndHashCode(callSuper=true)
public class Courier extends Person {


    public Courier() {}
    
    public Courier(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String phoneNumber,
        String blaBla) {

        super(firstName, lastName, email, phoneNumber);
        this.blaBla = blaBla;
    }

    private String blaBla;
}

// TODO: 09.04.2020 Добавить сущность автомобиль и подвязать к курьеру.

package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courier")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Courier extends Person {

    private String blaBla;

    //public Courier(){}
}

// TODO: 09.04.2020 Добавить сущность автомобиль и подвязать к курьеру.

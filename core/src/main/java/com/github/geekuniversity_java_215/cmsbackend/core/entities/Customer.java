package com.github.geekuniversity_java_215.cmsbackend.core.entities;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer")
@Data
@EqualsAndHashCode(callSuper=true)
public class Customer extends Person {

    public Customer() {}
    
    public Customer(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }
}
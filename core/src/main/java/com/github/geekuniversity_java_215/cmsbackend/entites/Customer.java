package com.github.geekuniversity_java_215.cmsbackend.entites;


import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer extends Person {

    public Customer(){}

}
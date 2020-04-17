package com.github.geekuniversity_java_215.cmsbackend.entites;


import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer extends Person {

//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected Long id;

    public Customer(){}

}
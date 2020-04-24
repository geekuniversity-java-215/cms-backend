package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class TestEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "testentity_id_seq")
    private Long id;

    @Column(name="test")
    private String test;


    public TestEntity(String test) {
        this.test = test;
    }
}

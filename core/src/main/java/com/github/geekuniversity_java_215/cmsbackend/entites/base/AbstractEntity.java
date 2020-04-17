package com.github.geekuniversity_java_215.cmsbackend.entites.base;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
public abstract class AbstractEntity extends AbstractEntityNoId {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected AbstractEntity() {}

}


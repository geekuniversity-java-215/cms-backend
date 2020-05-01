package com.github.geekuniversity_java_215.cmsbackend.core.entities.base;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@MappedSuperclass
public abstract class AbstractEntity implements Serializable, IdGetter {

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    protected Instant created;


    @Column(name = "updated")
    @UpdateTimestamp
    protected Instant updated;

    protected AbstractEntity() {}

    public Instant getCreated() {
        return created;
    }

    public Instant getUpdated() {
        return updated;
    }
}

package com.github.geekuniversity_java_215.cmsbackend.core.entities.base;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Data
public abstract class AbstractEntity implements Serializable {

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    protected Instant created;


    @Column(name = "updated")
    @UpdateTimestamp
    protected Instant updated;

    protected Boolean enabled = true;

    protected AbstractEntity() {}

    private Instant setCreated() {
        return created;
    }

    private Instant setUpdated() {
        return updated;
    }

}

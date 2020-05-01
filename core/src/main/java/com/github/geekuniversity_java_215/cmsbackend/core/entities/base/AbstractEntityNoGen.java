package com.github.geekuniversity_java_215.cmsbackend.core.entities.base;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * Base for simple entities and
 * entities with InheritanceType.TABLE_PER_CLASS
 */
@MappedSuperclass
public abstract class AbstractEntityNoGen implements Serializable, IdGetter {

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    protected Instant created;


    @Column(name = "updated")
    @UpdateTimestamp
    protected Instant updated;

    protected AbstractEntityNoGen() {}

    public Instant getCreated() {
        return created;
    }

    public Instant getUpdated() {return updated;}


}

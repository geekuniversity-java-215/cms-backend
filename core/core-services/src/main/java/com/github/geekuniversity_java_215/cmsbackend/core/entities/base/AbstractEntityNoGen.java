package com.github.geekuniversity_java_215.cmsbackend.core.entities.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
@Getter
public abstract class AbstractEntityNoGen implements Serializable, IdGetter {

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    protected Instant created;


    @Column(name = "updated")
    @UpdateTimestamp
    protected Instant updated;

    @Setter
    @Getter
    protected Boolean enabled = true;

    protected AbstractEntityNoGen() {}


}

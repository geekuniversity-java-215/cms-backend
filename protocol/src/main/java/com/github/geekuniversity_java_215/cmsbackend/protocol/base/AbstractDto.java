package com.github.geekuniversity_java_215.cmsbackend.protocol.base;

import java.io.Serializable;
import java.time.Instant;

public abstract class AbstractDto implements Serializable {

    protected Long id;
    protected Instant created; // Можно поменять на Long
    protected Instant updated; // Можно поменять на Long

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "AbstractDto{" +
            "id=" + id +
            ", created=" + created +
            ", updated=" + updated +
            '}';
    }
}

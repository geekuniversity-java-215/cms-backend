package com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
public abstract class Token extends AbstractEntity {

    @Column(name = "expired_at", updatable = false)
    protected Instant expiredAt;

    protected Token() {}
    public Token(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }
}

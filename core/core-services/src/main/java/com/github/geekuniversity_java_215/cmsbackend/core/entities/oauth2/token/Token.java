package com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
@Getter
public abstract class Token {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    protected Instant created;

    @Column(name = "updated")
    @UpdateTimestamp
    protected Instant updated;

    @Column(name = "expired_at", updatable = false)
    @Getter
    protected Instant expiredAt;

    protected Token() {}
    public Token(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }

}

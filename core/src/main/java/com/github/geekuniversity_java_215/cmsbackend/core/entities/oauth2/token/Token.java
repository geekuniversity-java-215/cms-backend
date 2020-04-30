package com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@Getter
public abstract class Token extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "token_id_seq")
    protected Long id;

    @Column(name = "expired_at", updatable = false)
    protected Instant expiredAt;

//    @Enumerated(EnumType.STRING)
//    private TokenType type;

    @NotNull
    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName="id")
    protected Person person;

    protected Token() {}

    public Token(Person person, boolean enabled, Instant expiredAt) {
        this.person = person;
        this.enabled = enabled;
        this.expiredAt = expiredAt;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public Person getPerson() {return person;}
}

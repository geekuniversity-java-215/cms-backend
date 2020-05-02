package com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(indexes = {
    @Index(name = "idx_access_token_created", columnList = "created"),
    @Index(name = "idx_access_token_expired_at", columnList = "expired_at")})
public class AccessToken extends Token {

    protected AccessToken() {}

    @OneToOne
    @JoinColumn(name = "refresh_token_id", referencedColumnName = "id")
    private RefreshToken refreshToken;

    public AccessToken(Person user, RefreshToken refreshToken, Instant expiredAt) {
        super(user, expiredAt);
        this.refreshToken = refreshToken;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }
}

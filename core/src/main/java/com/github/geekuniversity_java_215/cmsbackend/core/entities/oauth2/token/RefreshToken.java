package com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(indexes = {
    @Index(name = "idx_refresh_token_created", columnList = "created"),
    @Index(name = "idx_refresh_token_expired_at", columnList = "expired_at")})
public class RefreshToken extends Token {

    // link from refresh token to access token
    @OneToOne(mappedBy = "refreshToken", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccessToken accessToken;

    protected RefreshToken() {}

    public RefreshToken(Person person, Instant expiredAt) {
        super(person, expiredAt);
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}

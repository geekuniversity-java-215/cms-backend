package com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token;

import lombok.Getter;

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
    @Getter
    private RefreshToken refreshToken;

    public AccessToken(RefreshToken refreshToken, Instant expiredAt) {
        super(expiredAt);
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "id=" + id +
                ", expiredAt=" + expiredAt +
                '}';
    }
}

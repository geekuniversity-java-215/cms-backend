package com.github.geekuniversity_java_215.cmsbackend.authserver.entities;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.Instant;

/**
 *  access_token that moved to blacklist (to share it with resource servers)
 */
@Entity
@Table(indexes = {
        @Index(name = "idx_blacklisted_created", columnList = "created"),
        @Index(name = "idx_blacklisted_expired_at", columnList = "expired_at")})
public class BlacklistedToken extends AbstractEntity {

    // Id бывшего access_token
    @Column(unique=true)
    @Getter
    private Long tokenId;

    @Column(name = "expired_at", updatable = false)
    @Getter
    private Instant expiredAt;

    protected BlacklistedToken() {}

    public BlacklistedToken(Long tokenId, Instant expiredAt) {
        this.tokenId = tokenId;
        this.expiredAt = expiredAt;
    }
}

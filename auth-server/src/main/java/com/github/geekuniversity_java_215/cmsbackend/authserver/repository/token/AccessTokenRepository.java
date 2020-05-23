package com.github.geekuniversity_java_215.cmsbackend.authserver.repository.token;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.AccessToken;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccessTokenRepository extends CustomRepository<AccessToken, Long> {

    @Modifying
    @Query("DELETE FROM AccessToken t WHERE t.expiredAt < CURRENT_TIMESTAMP")
    void vacuum();
}

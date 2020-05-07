package com.github.geekuniversity_java_215.cmsbackend.authserver.repository;

import com.github.geekuniversity_java_215.cmsbackend.authserver.entities.BlacklistedToken;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistedTokenRepository extends CustomRepository<BlacklistedToken, Long> {

    @Modifying
    @Query("DELETE FROM BlacklistedToken t WHERE t.expiredAt < CURRENT_TIMESTAMP")
    void vacuum();

    List<BlacklistedToken> findByIdGreaterThanEqual(@Param("from")Long from);
}

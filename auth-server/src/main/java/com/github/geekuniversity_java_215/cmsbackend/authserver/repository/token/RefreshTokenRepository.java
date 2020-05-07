package com.github.geekuniversity_java_215.cmsbackend.authserver.repository.token;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.RefreshToken;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends CustomRepository<RefreshToken, Long> {

    void findAllByUserUsername(String username);



//    @Modifying
//    @Query("UPDATE RefreshToken t set t.enabled = true WHERE t.id = :id")
//    void approveById(@Param("id")Long id);

    @Modifying
    @Query("DELETE FROM RefreshToken t WHERE t.expiredAt < CURRENT_TIMESTAMP")
    void vacuum();

    void deleteByUserUsername(String username);
    void deleteByUser(User user);
}

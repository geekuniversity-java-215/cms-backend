package com.github.geekuniversity_java_215.cmsbackend.authserver.repository;

import com.github.geekuniversity_java_215.cmsbackend.authserver.entities.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnconfirmedUserRepository extends CustomRepository<UnconfirmedUser, Long> {

    Optional<UnconfirmedUser> findOneByLogin(String login);
    Optional<UnconfirmedUser> findOneByLastNameAndFirstName(String lastName, String firstName);

    @Query("FROM UnconfirmedUser u " +
            "WHERE " +
            "u.login = :#{#user.login} OR " +
            "u.lastName = :#{#user.lastName} AND u.firstName = :#{#user.firstName} OR " +
            "u.email = :#{#user.email} OR " +
            "u.phoneNumber = :#{#user.phoneNumber}")
    boolean checkIfExists(@Param("user")UnconfirmedUser user);

    @Modifying
    @Query("DELETE FROM UnconfirmedUser u WHERE u.expiredAt < CURRENT_TIMESTAMP")
    void vacuum();
}

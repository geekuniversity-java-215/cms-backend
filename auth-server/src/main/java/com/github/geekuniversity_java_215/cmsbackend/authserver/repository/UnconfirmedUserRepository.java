package com.github.geekuniversity_java_215.cmsbackend.authserver.repository;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnconfirmedUserRepository extends CustomRepository<UnconfirmedUser, Long> {

    Optional<UnconfirmedUser> findOneByUsername(String username);
    Optional<UnconfirmedUser> findOneByLastNameAndFirstName(String lastName, String firstName);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
        "FROM UnconfirmedUser u " +
        "WHERE " +
        "lower(u.username) = :#{#user.username.toLowerCase()} OR " +
        "lower(u.lastName) = :#{#user.lastName.toLowerCase()} AND u.firstName = :#{#user.firstName} OR " +
        "lower(u.email) = :#{#user.email.toLowerCase()} OR " +
        "u.phoneNumber = :#{#user.phoneNumber}")
    boolean checkIfExists(@Param("user") UnconfirmedUser user);




    @Modifying
    @Query("DELETE FROM UnconfirmedUser u WHERE u.expiredAt < CURRENT_TIMESTAMP")
    void vacuum();
}

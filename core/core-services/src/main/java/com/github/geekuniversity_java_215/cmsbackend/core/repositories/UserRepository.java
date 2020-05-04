package com.github.geekuniversity_java_215.cmsbackend.core.repositories;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CustomRepository<User, Long> {

    Optional<User> findOneByLogin(String login);
    Optional<User> findOneByLastNameAndFirstName(String lastName, String firstName);

    @Query("FROM User u " +
            "WHERE " +
            "u.login = :#{#user.login} OR " +
            "u.lastName = :#{#user.lastName} AND u.firstName = :#{#user.firstName} OR " +
            "u.email = :#{#user.email} OR " +
            "u.phoneNumber = :#{#user.phoneNumber}")
    boolean checkIfExists(@Param("user")User user);
}

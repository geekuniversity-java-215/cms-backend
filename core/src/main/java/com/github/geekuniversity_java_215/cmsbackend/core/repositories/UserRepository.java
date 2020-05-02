package com.github.geekuniversity_java_215.cmsbackend.core.repositories;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.User;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CustomRepository<User, Long> {

    Optional<User> findOneByLogin(String login);
    Optional<User> findOneByLastNameAndFirstName(String lastName, String firstName);
}

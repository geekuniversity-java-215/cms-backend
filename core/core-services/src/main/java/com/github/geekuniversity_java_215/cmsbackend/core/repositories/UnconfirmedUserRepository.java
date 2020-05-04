package com.github.geekuniversity_java_215.cmsbackend.core.repositories;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnconfirmedUserRepository extends CustomRepository<UnconfirmedUser, Long> {

    Optional<UnconfirmedUser> findOneByLogin(String login);
    Optional<UnconfirmedUser> findOneByLastNameAndFirstName(String lastName, String firstName);
}

package com.github.geekuniversity_java_215.cmsbackend.core.repositories;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends CustomRepository<Courier, Long> {

    Optional<Courier> findOneByUserLastNameAndUserFirstName(String lastName, String firstName);
    Optional<Courier> findOneByUser(User user);
    Optional<Courier> findOneByUserUsername(String username);
}

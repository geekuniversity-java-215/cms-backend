package com.github.geekuniversity_java_215.cmsbackend.core.repositories;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CustomRepository<Person, Long> {

    Optional<Person> findOneByLogin(String login);

    Optional<Person> findOneByLastNameAndFirstName(String lastName, String firstName);
}

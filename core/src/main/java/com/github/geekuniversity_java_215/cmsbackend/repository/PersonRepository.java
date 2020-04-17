package com.github.geekuniversity_java_215.cmsbackend.repository;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.repository.base.CustomRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonRepository extends CustomRepository<Person, Long>, JpaSpecificationExecutor<Person> {
}

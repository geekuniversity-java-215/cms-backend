package com.github.geekuniversity_java_215.cmsbackend.service;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.repository.PersonRepository;
import com.github.geekuniversity_java_215.cmsbackend.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


// В таком виде, как пока есть, ему, видимо, до барабана с какой конкретно реализацией Person иметь дело,
// работает и с Courier и с Customer
@Service
@Transactional
public class PersonService extends BaseService<Person> {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        super(personRepository);
        this.personRepository = personRepository;
    }

}

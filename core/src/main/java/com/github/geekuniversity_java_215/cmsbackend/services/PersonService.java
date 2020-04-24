package com.github.geekuniversity_java_215.cmsbackend.services;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.repository.PersonRepository;
import com.github.geekuniversity_java_215.cmsbackend.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// В таком виде, как пока есть, ему, видимо, до барабана с какой конкретно реализацией Person иметь дело,
// работает и с Courier и с Customer
@Service
@Transactional
public class PersonService extends BaseService<Person> {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        super(personRepository);
        this.personRepository = personRepository;
    }

}

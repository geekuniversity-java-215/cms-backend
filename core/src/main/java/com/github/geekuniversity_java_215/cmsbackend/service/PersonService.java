package com.github.geekuniversity_java_215.cmsbackend.service;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.repository.PersonRepository;
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
public class PersonService {


    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> findAllById(List<Long> listId) {

        return personRepository.findAllById(listId);
    }

    public List<Person> findAll(Specification<Person> spec) {
        return personRepository.findAll(spec);
    }

    public Person save(Person person) {

        return personRepository.save(person);
    }

    public void delete(Person person) {

        personRepository.delete(person);
    }

    public Page<Person> findAll(Specification<Person> spec, PageRequest pageable) {

        return personRepository.findAll(spec, pageable);
    }
}

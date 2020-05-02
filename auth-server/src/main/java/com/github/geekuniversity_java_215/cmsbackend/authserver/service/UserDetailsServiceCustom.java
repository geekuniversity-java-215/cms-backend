package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import com.github.geekuniversity_java_215.cmsbackend.authserver.data.UserPrincipalCustom;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public UserDetailsServiceCustom(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {

        Person person = personRepository.findOneByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(login));

        return new UserPrincipalCustom(person);
    }
}

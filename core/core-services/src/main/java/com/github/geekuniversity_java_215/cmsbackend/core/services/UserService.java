package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.UserRepository;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class UserService extends BaseRepoAccessService<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public Optional<User> findByLogin(String login) {
        Optional<User> result = Optional.empty();
        if (!StringUtils.isBlank(login)) {
            result = userRepository.findOneByLogin(login);
        }
        return result;
    }


    /**
     * Find User by LastNameFirstName
     * @param lastName
     * @param firstName
     * @return
     */
    public Optional<User> findByFullName(String lastName, String firstName) {
        Optional<User> result = Optional.empty();
        if (!StringUtils.isBlank(lastName) && !StringUtils.isBlank(firstName)) {
            result = userRepository.findOneByLastNameAndFirstName(lastName, firstName);
        }
        return result;
    }

}

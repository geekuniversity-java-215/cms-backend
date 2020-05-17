package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.repositories.UserRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.utils.interfaces.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class UserService extends BaseRepoAccessService<User> {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    @Autowired
    public UserService(AuthenticationFacade authenticationFacade,
                       UserRepository userRepository) {
        super(userRepository);
        this.authenticationFacade = authenticationFacade;
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> result = Optional.empty();
        if (!StringUtils.isBlank(username)) {
            result = userRepository.findOneByUsername(username);
        }
        return result;
    }


    public User getCurrentAuthenticatedUser() throws UsernameNotFoundException {
        User result;
        String username = authenticationFacade.getAuthentication().getName();
        result = userRepository.findOneByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
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

    /**
     * Check if user already exists by username OR FullName OR email OR phoneNumber
     * @param user
     * @return
     */
    public boolean checkIfExists(User user) {
        return userRepository.checkIfExists(user);
    }

}

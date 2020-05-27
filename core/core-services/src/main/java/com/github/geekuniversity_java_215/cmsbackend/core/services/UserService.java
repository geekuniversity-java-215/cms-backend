package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.UserDetailsCustom;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.UserRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public Optional<User> findByUsername(String username) {
        Optional<User> result = Optional.empty();
        if (!StringUtils.isBlank(username)) {
            result = userRepository.findOneByUsername(username);
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

    /**
     * Check if user already exists by username OR FullName OR email OR phoneNumber
     * @param user
     * @return
     */
    public boolean checkIfExists(User user) {
        return userRepository.checkIfExists(user);
    }




    /**
     * Get current authenticated user userName
     * @return User
     */
    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        return userDetails.getUsername();
    }


    /**
     * Get current authenticated user
     * @return User
     */
    public Optional<User> getCurrentUser() {
        return findByUsername(getCurrentUsername());
    }

}

package com.github.geekuniversity_java_215.cmsbackend.core.services.user;


import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.UserDetailsCustom;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.UserRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;


@Service
@Transactional
public class UserService extends BaseRepoAccessService<User> {


    private final UserRepository userRepository;

    @Autowired
    public UserService( UserRepository userRepository) {
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

        String result;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            result = userDetails.getUsername();
        }
        else {
            result = authentication.getName();
        }
        return result;
    }

    public static Collection<? extends GrantedAuthority> getCurrentUserAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }


    /**
     * Get current authenticated user
     * @return User
     */
    public User getCurrent() {

        User result;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Auth server
        if (authentication.getPrincipal() instanceof UserDetailsCustom) {
            UserDetailsCustom userDetails = (UserDetailsCustom)authentication.getPrincipal();
            return userDetails.getUser();
        }
        // Resource server
        else {
            //noinspection OptionalGetWithoutIsPresent
            result = findByUsername(authentication.getName()).get();

        }
        return result;
    }

    @Override
    public Optional<User> findByIdEager(Long id) {
        return userRepository.findById(id, EntityGraphs.named(User.FULL_ENTITY_GRAPH));
    }
}

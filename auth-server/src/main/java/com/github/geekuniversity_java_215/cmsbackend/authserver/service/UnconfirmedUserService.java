package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.authserver.repository.UnconfirmedUserRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;


@Service
@Transactional
public class UnconfirmedUserService extends BaseRepoAccessService<UnconfirmedUser> {

    private final UnconfirmedUserRepository unconfirmedUserRepository;

    public UnconfirmedUserService(UnconfirmedUserRepository unconfirmedUserRepository) {
        super(unconfirmedUserRepository);
        this.unconfirmedUserRepository = unconfirmedUserRepository;
    }

    public Optional<UnconfirmedUser> findByUsername(String username) {
        Optional<UnconfirmedUser> result = Optional.empty();
        if (!StringUtils.isBlank(username)) {
            result = unconfirmedUserRepository.findOneByUsername(username);
        }
        return result;
    }

    /**
     * Check if user already exists by username OR FullName OR email OR phoneNumber
     * @param newUser
     * @return
     */
    public boolean checkIfExists(UnconfirmedUser newUser) {
        return unconfirmedUserRepository.checkIfExists(newUser);
    }
}

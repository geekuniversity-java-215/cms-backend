package com.github.geekuniversity_java_215.cmsbackend.core.services.user;

import com.github.geekuniversity_java_215.cmsbackend.core.repositories.UserRoleRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserRoleService extends BaseRepoAccessService<UserRole> {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        super(userRoleRepository);
        this.userRoleRepository = userRoleRepository;
    }

    public Optional<UserRole> findByName(String name) {
        return userRoleRepository.findOneByName(name);
    }

}

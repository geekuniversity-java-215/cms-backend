package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import com.github.geekuniversity_java_215.cmsbackend.authserver.repository.UserRoleRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void save(UserRole role) {
        userRoleRepository.save(role);
    }

    public UserRole findByName(String name) {
        return userRoleRepository.findOneByName(name).orElse(null);
    }

}

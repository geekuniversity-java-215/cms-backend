package com.github.geekuniversity_java_215.cmsbackend.authserver.repository;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;

import java.util.Optional;

public interface UserRoleRepository extends CustomRepository<UserRole, Long> {

    Optional<UserRole> findOneByName(String name);
}

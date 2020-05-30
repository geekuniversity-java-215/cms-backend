package com.github.geekuniversity_java_215.cmsbackend.core.entities.user;

import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

@Component
@Slf4j
public class UserToPersistListener {

    static private UserRoleService userRoleService;

    @Autowired
    public void init(UserRoleService userRoleService){
        UserToPersistListener.userRoleService = userRoleService;
    }

    @PrePersist
    @PreUpdate
    public void methodExecuteBeforeSave(User user) {

        // Make shure that User contains persisted UserRoles
        // So CascadeType.MERGE will be successful
        for (UserRole role : new HashSet<>(user.getRoles())) {

            if (role.getId() == null) {
                user.getRoles().remove(role);
                user.getRoles().add(userRoleService.findByName(role.getName())
                    .orElseThrow(() -> new IllegalArgumentException("UserRole " + role.getName() + "not found")));
            }
        }
    }
}

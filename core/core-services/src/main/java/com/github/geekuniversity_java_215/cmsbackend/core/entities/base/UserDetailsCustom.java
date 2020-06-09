package com.github.geekuniversity_java_215.cmsbackend.core.entities.base;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsCustom extends UserDetails {
    // Better don't do this - user fields  may change during request scope
    User getUser();
}

package com.github.geekuniversity_java_215.cmsbackend.core.entities.base;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsCustom extends UserDetails {
    User getUser();
}

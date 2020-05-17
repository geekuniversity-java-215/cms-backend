package com.github.geekuniversity_java_215.cmsbackend.utils.interfaces;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();
}

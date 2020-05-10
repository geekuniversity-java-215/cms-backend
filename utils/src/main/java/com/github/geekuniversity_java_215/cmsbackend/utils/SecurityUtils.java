package com.github.geekuniversity_java_215.cmsbackend.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class SecurityUtils {

    public Set<GrantedAuthority> rolesToGrantedAuthority(Set<String> authorities) {
        //noinspection unchecked
        return authorities
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public Set<GrantedAuthority> rolesToGrantedAuthority(Object authorities) {
        //noinspection unchecked
        return ((List<String>)authorities)
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}

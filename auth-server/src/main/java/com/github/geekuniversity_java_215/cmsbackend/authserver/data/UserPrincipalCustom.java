package com.github.geekuniversity_java_215.cmsbackend.authserver.data;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserPrincipalCustom implements UserDetails {

    private User user;

    public UserPrincipalCustom(User user) {
        this.user = user;
    }



    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(c -> new SimpleGrantedAuthority(c.getName())).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {return user.getLogin();}

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getEnabled();
    }




}
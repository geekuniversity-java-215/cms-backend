package com.github.geekuniversity_java_215.cmsbackend.authserver.data;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserPrincipalCustom implements UserDetails {

    private Person person;

    public UserPrincipalCustom(Person person) {
        this.person = person;
    }



    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return person.getRoles().stream().map(c -> new SimpleGrantedAuthority(c.getName())).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {return person.getLogin();}

    @Override
    public boolean isEnabled() {
        return person.getEnabled();
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
        return person.getEnabled();
    }




}
package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.antMatcher("/api/**").anonymous()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable();
    }

}

package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.configuration;

import com.github.geekuniversity_java_215.cmsbackend.core.configurations.filtres.BearerRequestResourceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BearerRequestResourceFilter bearerRequestResourceFilter;

    @Autowired
    public WebSecurityConfig(BearerRequestResourceFilter bearerRequestResourceFilter) {
        this.bearerRequestResourceFilter = bearerRequestResourceFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable()
            .addFilterAfter(bearerRequestResourceFilter, LogoutFilter.class);
    }
}
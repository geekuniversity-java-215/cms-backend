package com.github.geekuniversity_java_215.cmsbackend.chat.configs;

import com.github.geekuniversity_java_215.cmsbackend.core.configurations.filters.BearerRequestResourceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class ChatSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BearerRequestResourceFilter bearerRequestResourceFilter;

    @Autowired
    public ChatSecurityConfig(BearerRequestResourceFilter bearerRequestResourceFilter) {
        this.bearerRequestResourceFilter = bearerRequestResourceFilter;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .and().authorizeRequests().antMatchers("/chat/**").authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(bearerRequestResourceFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.antMatcher("/chat/**").authorizeRequests().anyRequest().authenticated()
//            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and().csrf().disable()
//            .addFilterAfter(bearerRequestResourceFilter, LogoutFilter.class);
//    }
}

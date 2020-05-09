package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.configuration;

import com.github.geekuniversity_java_215.cmsbackend.core.configurations.filtres.BearerRequestResourceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * Resource access
     * Authorisation: Bearer (access_token)
     */
    @Configuration
    public static class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

        private final BearerRequestResourceFilter bearerRequestResourceFilter;

        @Autowired
        public AdminWebSecurityConfig(BearerRequestResourceFilter bearerRequestResourceFilter) {
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
  
/*

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.antMatcher("/api/**").anonymous()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable();
    }
*/

}

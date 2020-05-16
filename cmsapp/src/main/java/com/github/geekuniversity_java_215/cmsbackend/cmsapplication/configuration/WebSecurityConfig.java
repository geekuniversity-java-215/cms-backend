package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.configuration;

import com.github.geekuniversity_java_215.cmsbackend.core.configurations.filters.BearerRequestResourceFilter;
import com.github.geekuniversity_java_215.cmsbackend.core.configurations.filters.CorsAllowAllFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BearerRequestResourceFilter bearerRequestResourceFilter;
    private final CorsAllowAllFilter corsAllowAllFilter;


    @Bean
    public FilterRegistrationBean<BearerRequestResourceFilter> bearerRequestFilterRegistration(BearerRequestResourceFilter filter) {
        FilterRegistrationBean<BearerRequestResourceFilter> result = new FilterRegistrationBean<>(filter);
        result.setEnabled(false);
        return result;
    }
    @Bean
    public FilterRegistrationBean<CorsAllowAllFilter> corsAllowAllFilterRegistration(CorsAllowAllFilter filter) {
        FilterRegistrationBean<CorsAllowAllFilter> result = new FilterRegistrationBean<>(filter);
        result.setEnabled(false);
        return result;
    }


    @Autowired
    public WebSecurityConfig(BearerRequestResourceFilter bearerRequestResourceFilter, CorsAllowAllFilter corsAllowAllFilter) {
        this.bearerRequestResourceFilter = bearerRequestResourceFilter;
        this.corsAllowAllFilter = corsAllowAllFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable()
            .addFilterBefore(corsAllowAllFilter, ChannelProcessingFilter.class)
            .addFilterAfter(bearerRequestResourceFilter, LogoutFilter.class);
    }
}
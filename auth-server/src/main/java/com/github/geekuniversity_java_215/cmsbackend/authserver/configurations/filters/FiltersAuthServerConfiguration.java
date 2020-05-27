package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.filters;

import com.github.geekuniversity_java_215.cmsbackend.core.configurations.filters.CorsAllowAllFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltersAuthServerConfiguration {

    // Отключаем к хренам BearerRequestFilter и BasicAuthFilterCustom,
    // иначе spring boot автоматом запхнет их в filterChain (servlet filter chain)
    // https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-disable-registration-of-a-servlet-or-filter
    // (Этот фильтр должен работать только в springSecurityFilterChain)
    // Хотите проще(в аннотации самого фильтра отключить)? - нате
    // https://github.com/spring-projects/spring-boot/issues/16500
    //
    // Как робят фильтры в spring(security)
    // https://habr.com/ru/post/346628/
    @Bean
    public FilterRegistrationBean<BearerRequestFilter> bearerRequestFilterRegistration(BearerRequestFilter filter) {
        FilterRegistrationBean<BearerRequestFilter> result = new FilterRegistrationBean<>(filter);
        result.setEnabled(false);
        return result;
    }

    @Bean
    public FilterRegistrationBean<BasicAuthRequestFilter> basicAuthCustomFilterRegistration(BasicAuthRequestFilter filter) {
        FilterRegistrationBean<BasicAuthRequestFilter> result = new FilterRegistrationBean<>(filter);
        result.setEnabled(false);
        return result;
    }
}

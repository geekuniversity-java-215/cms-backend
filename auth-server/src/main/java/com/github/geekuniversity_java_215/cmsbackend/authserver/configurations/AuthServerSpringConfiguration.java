package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthServerSpringConfiguration {

    // JWT ISSUER field
    @Value("auth-server.issuer")
    @Getter(AccessLevel.PUBLIC)
    private String ISSUER;

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }


}

package com.github.geekuniversity_java_215.cmsbackend.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class PropertiesConfiguration {

    @Configuration
    @Profile("default")
    @PropertySource(value ={
        "classpath:application.properties",
        "classpath:core.properties",
        "classpath:mail.properties",
        "classpath:payment.properties"},
        ignoreResourceNotFound=true)
    static class DefaultProperties {}

    @Configuration
    @Profile("!default")
    @PropertySource(value = {
        "classpath:application-${spring.profiles.active}.properties",
        "classpath:core-${spring.profiles.active}.properties",
        "classpath:mail-${spring.profiles.active}.properties",
        "classpath:payment-${spring.profiles.active}.properties"},
        ignoreResourceNotFound=true)
    static class NonDefaultProperties {}
}
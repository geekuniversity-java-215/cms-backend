package com.github.geekuniversity_java_215.cmsbackend.geodata.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class GeodataPropertiesConfiguration {

    @Configuration
    @Profile("default")
    @PropertySource(value = {
        "classpath:geodata.properties"})
    static class DefaultProperties {}

    @Configuration
    @Profile("!default")
    @PropertySource(value = {
        "classpath:geodata-${spring.profiles.active}.properties"})
    static class NonDefaultProperties {}
}

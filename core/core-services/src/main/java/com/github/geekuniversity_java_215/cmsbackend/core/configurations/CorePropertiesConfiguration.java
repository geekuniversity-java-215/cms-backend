package com.github.geekuniversity_java_215.cmsbackend.core.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

// Позволяет не указывать конкретный имена файлов
// для @ConfigurationProperties в JrpcClientProperties.java
// (так как у нас минимум два Spring профиля - default и dev)
@Configuration
public class CorePropertiesConfiguration {

    @Configuration
    @Profile("default")
    @PropertySource(value = {
        "classpath:core-services.properties"})
    static class DefaultProperties {}

    @Configuration
    @Profile("!default")
    @PropertySource(value = {
        "classpath:core-services-${spring.profiles.active}.properties"})
    static class NonDefaultProperties {}
}

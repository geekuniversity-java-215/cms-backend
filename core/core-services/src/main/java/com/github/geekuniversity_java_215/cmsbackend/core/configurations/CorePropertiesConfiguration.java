package com.github.geekuniversity_java_215.cmsbackend.core.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

// Загружатель .properties для различных Spring конфигураций.
// Позволяет не указывать конкретные имена файлов для @ConfigurationProperties.
// Позволяет загружать файлы настроек(.properties), именованные в соответствии с активным Spring profile
// (так как у нас минимум два Spring профиля - default и dev)
@Configuration
public class CorePropertiesConfiguration {

    @Configuration
    @Profile("default")
    @PropertySource(value = {"classpath:core-services.properties"})
    static class DefaultProperties {}

    @Configuration
    @Profile("!default")
    @PropertySource(value = {"classpath:core-services-${spring.profiles.active}.properties"})
    static class NonDefaultProperties {}
}

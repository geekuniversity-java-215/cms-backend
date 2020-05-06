package com.github.geekuniversity_java_215.cmsbackend.mail.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Загружатель Properties для конфигураций.
 * <br>Такое делаем в своем модуле, если используются .properties
 * Главное префиксы ключей внутри .properties
 * для разных модулей делать разными иначе перекроются.
 */
@Configuration
public class MailPropertiesConfiguration {

    @Configuration
    @Profile("default")
    @PropertySource(value ={
        "classpath:mail.properties"})
    static class DefaultProperties {}

    @Configuration
    @Profile("!default")
    @PropertySource(value = {
        "classpath:mail-${spring.profiles.active}.properties"})
    static class NonDefaultProperties {}
}
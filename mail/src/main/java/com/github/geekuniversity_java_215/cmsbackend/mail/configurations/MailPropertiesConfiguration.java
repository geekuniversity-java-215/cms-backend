package com.github.geekuniversity_java_215.cmsbackend.mail.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Загружатель Properties для различных конфигураций.
 * Включаем все настройки разом.
 *
 * mvn test сперва собирает модуль(снизу-вверх по зависимостям),
 * потом тестирует, потом начинает собирать модуль выше.
 * Соответственно для нижних модулей не находит .properties, которые используются модулем, который выше.
 * (так как модуль выше еще не собран)
 *
 * Поэтому ignoreResourceNotFound=true, иначе бы пришлось для каждого модуля
 * указывать .properties отдельно, а так - общая помойка, ну и норм.
 * (Все равно лишние настройки в библиотеку не попадут, правда пропадет "Exception property file [blabla] not found")
 * Главное префиксы ключей внутри .properties для разных модулей делать разными иначе перекроются.
 */
@Configuration
public class MailPropertiesConfiguration {

    @Configuration
    @Profile("default")
    @PropertySource(value ={
        "classpath:mail.properties"}
        /*ignoreResourceNotFound=true*/)
    static class DefaultProperties {}

    @Configuration
    @Profile("!default")
    @PropertySource(value = {
        "classpath:mail-${spring.profiles.active}.properties"}
        /*ignoreResourceNotFound=true*/)
    static class NonDefaultProperties {}

}
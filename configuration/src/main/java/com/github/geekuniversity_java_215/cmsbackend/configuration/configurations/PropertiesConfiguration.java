package com.github.geekuniversity_java_215.cmsbackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Включаем все настройки разом.
 *
 * mvn test сперва собирает модуль(снизу-вверх по зависимостям),
 * потом тестирует, потом начинает собирать модуль выше.
 * Соответственно для нижних модулей не находит .properties, которые используются модулем, который выше.
 * (так как модуль выше еще не собран)
 *
 * Поэтому ignoreResourceNotFound=true, иначе бы пришлось для каждого модуля
 * указывать .properties отдельно, а так - общая помойка, ну и норм.
 * (Все равно лишние настройки в библиотеку не попадут, правда пропадет "Exception property [blabla] not found")
 * Главное префиксы ключей внутри .properties для разных модулей делать разными иначе перекроются.
 * По мне - так лучше чем один большой .properties в верхнем модуле,
 * т.к. верхних модулей может быть несколько.
 */
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
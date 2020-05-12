package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.*;

// Позволяет не указывать конкретный имена файлов
// для @ConfigurationProperties в JrpcClientProperties.java
// (так как у нас минимум два Spring профиля - default и dev)
@Configuration
public class JrpcClientPropertiesConfiguration {

    @Configuration
    @Profile("default")
    @PropertySource(value = {
        "classpath:jrpc-client.properties"})
    static class DefaultProperties {}

    @Configuration
    @Profile("!default")
    @PropertySource(value = {
        "classpath:jrpc-client-${spring.profiles.active}.properties"})
    static class NonDefaultProperties {}


//    // =================================================================
//
//
//    private final JrpcClientPropertiesFile jrpcClientPropertiesFile;
//
//    public JrpcClientPropertiesConfiguration(JrpcClientPropertiesFile jrpcClientPropertiesFile) {
//        this.jrpcClientPropertiesFile = jrpcClientPropertiesFile;
//    }
//
//
//    // Create bean from JrpcClientPropertiesFile
//    // but this bean could be altered in runtime
//    // While bean with @ConfigurationProperties can't
//    @Bean
//    @Primary
//    JrpcClientProperties jrpcClientProperties() {
//        JrpcClientProperties result = new JrpcClientProperties();
//        result = SerializationUtils.clone(jrpcClientPropertiesFile);
//        return result;
//    }
}

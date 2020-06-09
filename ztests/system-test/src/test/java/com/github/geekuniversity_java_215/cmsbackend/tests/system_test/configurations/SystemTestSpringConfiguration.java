package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.ClientConfigurationMapper;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientPropertiesFile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.dreamworkerln.spring.utils.rest.RestTemplateFactory;

@Configuration
@Slf4j
public class SystemTestSpringConfiguration {

    public static final String ADMIN ="adminProperties";
    //public static final String REGISTRAR ="registrarProperties";
    //public static final String USER ="userProperties";
    public static final String ANONYMOUS ="anonymousProperties";
    public static final String NEW_USER ="newUserProperties";

    public static final String CLIENT ="clientProperties";
    public static final String COURIER ="courierProperties";
    public static final String VASYA ="vasyaProperties";



    private final JrpcClientProperties defaultProperties;
    private final JrpcClientPropertiesFile defaultPropertiesFile;
    private final ApplicationContext context;
    private final ClientConfigurationMapper clientConfigurationMapper;

    public SystemTestSpringConfiguration(@Qualifier("jrpcClientProperties") JrpcClientProperties defaultProperties,
                                         JrpcClientPropertiesFile defaultPropertiesFile,
                                         ApplicationContext context,
                                         ClientConfigurationMapper clientConfigurationMapper) {
        this.defaultProperties = defaultProperties;
        this.defaultPropertiesFile = defaultPropertiesFile;
        this.context = context;
        this.clientConfigurationMapper = clientConfigurationMapper;
    }


    @Bean
    public RestTemplate restTemplate() {
        return RestTemplateFactory.getRestTemplate(100000);
    }

    // =========================================================================

    @Bean(ADMIN)
    JrpcClientProperties adminProperties() {
        JrpcClientProperties result = clientConfigurationMapper.toProperties(defaultPropertiesFile);
        result.getLogin().setUsername("root");
        result.getLogin().setPassword("toor");
        return result;
    }

    @Bean(ANONYMOUS)
    JrpcClientProperties anonymousProperties() {
        JrpcClientProperties result = clientConfigurationMapper.toProperties(defaultPropertiesFile);
        result.getLogin().setUsername("anonymous");
        result.getLogin().setPassword("anonymous");
        return result;
    }

    @Bean(NEW_USER)
    JrpcClientProperties newUserProperties() {
        JrpcClientProperties result = clientConfigurationMapper.toProperties(defaultPropertiesFile);
        result.getLogin().setUsername("newuser");
        result.getLogin().setPassword("newuser_password");
        return result;
    }

    @Bean(CLIENT)
    JrpcClientProperties clientProperties() {
        JrpcClientProperties result = clientConfigurationMapper.toProperties(defaultPropertiesFile);
        result.getLogin().setUsername("client");
        result.getLogin().setPassword("client_password");
        return result;
    }

    @Bean(COURIER)
    JrpcClientProperties courierProperties() {
        JrpcClientProperties result = clientConfigurationMapper.toProperties(defaultPropertiesFile);
        result.getLogin().setUsername("courier");
        result.getLogin().setPassword("courier_password");
        return result;
    }

    @Bean(VASYA)
    JrpcClientProperties vasyaProperties() {
        JrpcClientProperties result = clientConfigurationMapper.toProperties(defaultPropertiesFile);
        result.getLogin().setUsername("vasya");
        result.getLogin().setPassword("vasya_password");
        return result;
    }

    // ----------------------------------------------------

    /**
     * Копирует указанный бин в @Primary бин настроек клиента
     * @param propertiesName имя бина настроек клиента
     */
    public void switchJrpcClientProperties(String propertiesName) {

        JrpcClientProperties customProperties =
            context.getBean(propertiesName, JrpcClientProperties.class);

        BeanUtils.copyProperties(customProperties, defaultProperties);
    }
}

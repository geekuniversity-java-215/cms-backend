package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.ClientConfigurationMapper;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientPropertiesFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.dreamworkerln.spring.utils.rest.RestTemplateFactory;

@Configuration
@Slf4j
public class AuthServerTestSpringConfiguration {

    public static final String ADMIN ="adminProperties";
    public static final String REGISTRAR ="registrarProperties";
    public static final String USER ="userProperties";
    public static final String ANONYMOUS ="userProperties";


    private final JrpcClientProperties defaultProperties;
    private final JrpcClientPropertiesFile defaultPropertiesFile;
    private final ApplicationContext context;
    private final ClientConfigurationMapper clientConfigurationMapper;

    public AuthServerTestSpringConfiguration(JrpcClientProperties defaultProperties,
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

    @Bean(REGISTRAR)
    JrpcClientProperties registrarProperties() {
        JrpcClientProperties result = clientConfigurationMapper.toProperties(defaultPropertiesFile);
        result.getLogin().setUsername("registrar");
        result.getLogin().setPassword("registrar_password");
        return result;
    }

    @Bean(ANONYMOUS)
    JrpcClientProperties anonymousProperties() {
        JrpcClientProperties result = clientConfigurationMapper.toProperties(defaultPropertiesFile);
        result.getLogin().setUsername("anonymous");
        result.getLogin().setPassword("anonymous");
        return result;
    }



//    @Autowired
//    @Lazy
//    private JrpcClientProperties adminProperties;
//
//    @Autowired
//    @Lazy
//    private JrpcClientProperties registrarProperties;


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

package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.dreamworkerln.spring.utils.rest.RestTemplateFactory;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class AuthServerTestSpringConfiguration {

    public static final String ADMIN ="adminProperties";
    public static final String REGISTRAR ="registrarProperties";
    public static final String USER ="userProperties";

    @Autowired
    private ApplicationContext context;

    @Autowired
    @Qualifier("jrpcClientProperties")
    private JrpcClientProperties defaultProperties;

    @Bean
    public RestTemplate restTemplate() {
        return RestTemplateFactory.getRestTemplate(100000);
    }

    // =========================================================================

    @Bean(name = "adminProperties")
    @Qualifier("adminProperties")
    JrpcClientProperties adminProperties() {
        JrpcClientProperties result = new JrpcClientProperties();
        //BeanUtils.copyProperties(defaultProperties, result);
        result.getCredentials().setUsername("root");
        result.getCredentials().setPassword("toor");
        return result;
    }

    @Bean(name = REGISTRAR)
    JrpcClientProperties registrarProperties() {
        JrpcClientProperties result = new JrpcClientProperties();
        //BeanUtils.copyProperties(defaultProperties, result);
        result.getCredentials().setUsername("registrar");
        result.getCredentials().setPassword("registrar");
        return result;
    }



//    @Autowired
//    @Lazy
//    private JrpcClientProperties adminProperties;
//
//    @Autowired
//    @Lazy
//    private JrpcClientProperties registrarProperties;



    public void switchJrpcClientProperties(String propertiesName) {

        JrpcClientProperties customProperties =
            context.getBean("adminProperties", JrpcClientProperties.class);

        BeanUtils.copyProperties(customProperties, defaultProperties);
        
        log.info("current: {}", defaultProperties);



//        String userName = jrpcClientProperties.getCredentials().getUsername();
//        String password = jrpcClientProperties.getCredentials().getPassword();
//
//        customProperties.getCredentials().setUsername(userName);
//        customProperties.getCredentials().setPassword(password);
    }
}

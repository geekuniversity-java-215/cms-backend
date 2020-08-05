package com.github.geekuniversity_java_215.cmsbackend.core.configurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static com.github.geekuniversity_java_215.cmsbackend.core.data.constants.CorePropNames.SERVER_SERVLET_CONTEXT_PATH;

@Configuration
public class CoreSpringConfiguration {


    //private final Environment environment;

//    @Autowired
//    public CoreSpringConfiguration(Environment environment) {
//        this.environment = environment;
//    }

    @Bean
    public ObjectMapper objectMapper() {

        // ObjectMapper is threadsafe

        // allow convertation to/from Instant
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        // will write as string ISO 8601
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        return mapper;
    }

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}

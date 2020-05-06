package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.TokenDto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.beans.Transient;
import java.io.Serializable;


/**
 * Custom properties
 * https://www.baeldung.com/configuration-properties-in-spring-boot
 */
//
//@ContextConfiguration
//@Configuration
//@PropertySource("classpath:jrpc-client.properties")
// Калечит исходные классы, заворачивая их в прокси, потом вся логика, построенная на .getClass()
// и отладчик(просмотр значений) идет лесом

//@ConfigurationProperties("jrpc-client${spring.profiles.active}.properties")
//@Validated
@Configuration
@ConfigurationProperties
@Validated
@Data
@SuppressWarnings("ConfigurationProperties")
public class JrpcClientPropertiesFile implements Serializable {

    public static final String API_VERSION = "1.0";

    private Credentials credentials;

    private Server authServer;

    private Server resourceServer;

    private String apiURL;

    // =========================================================

    @ConfigurationProperties
    @Validated
    @Data
    //@ConfigurationProperties(prefix = "resourceserver")

    public static class Credentials {

        @Length(max = 4, min = 1)
//        private String authMethod;

        @NotBlank
        private String username;
        @NotBlank
        private String password;

        private String clientId;
        private String clientSecret;

        private TokenDto accessToken = TokenDto.EMPTY;
        private TokenDto refreshToken = TokenDto.EMPTY;

        @Override
        public String toString() {
            return "Credentials{" +
                "username='" + username + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
        }
    }

    @ConfigurationProperties
    @Validated
    @Data
    //@ConfigurationProperties(prefix = "resourceserver")
    public static class Server {


        @NotBlank
        //@Value("${authServer.hostname}")
        private String hostName;

        @Min(1025)
        @Max(65536)
        //@Value("${authServer.port}")
        @Value("3600")
        private int port;

        @Override
        public String toString() {
            return "Server{" +
                "hostName='" + hostName + '\'' +
                ", port=" + port +
                '}';
        }
    }

    /**
     *  Kludge, call this when creating bean by hands to complete it
     */
    public void build() {
        apiURL = String.format("http://%1$s:%2$s/api/%3$s/",
            this.resourceServer.hostName,
            this.resourceServer.port,
            API_VERSION);
    }

    @PostConstruct
    private void postConstruct() {
        build();
    }


}



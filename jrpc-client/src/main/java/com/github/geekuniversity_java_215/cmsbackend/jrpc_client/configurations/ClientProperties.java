package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.TokenDto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


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
//@Validated
//@ConfigurationProperties("${spring.profiles.active}.properties")

@Data
public class ClientProperties {

    private Credentials credentials;

    private Server authServer;

    private Server resourceServer;


    // =========================================================

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


    @Validated
    @Data
    //@ConfigurationProperties(prefix = "resourceserver")
    public static class Server {


        @NotBlank
        private String hostName;

        @Min(1025)
        @Max(65536)
        private int port;

//      @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
//      private String from;

        @Override
        public String toString() {
            return "Server{" +
                "hostName='" + hostName + '\'' +
                ", port=" + port +
                '}';
        }
    }
}



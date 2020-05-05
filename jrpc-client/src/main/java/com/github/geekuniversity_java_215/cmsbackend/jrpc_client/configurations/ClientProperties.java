package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.TokenDto;
import org.hibernate.validator.constraints.Length;
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
public class ClientProperties {

    private Credentials credentials;

    private Server authServer;

    private Server resourceServer;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Server getAuthServer() {
        return authServer;
    }

    public void setAuthServer(Server authServer) {
        this.authServer = authServer;
    }

    public Server getResourceServer() {
        return resourceServer;
    }

    public void setResourceServer(Server resourceServer) {
        this.resourceServer = resourceServer;
    }

    // =========================================================

    @Validated
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }



        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public TokenDto getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(TokenDto accessToken) {
            this.accessToken = accessToken;
        }

        public TokenDto getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(TokenDto refreshToken) {
            this.refreshToken = refreshToken;
        }

        @Override
        public String toString() {
            return "Credentials{" +
                   "username='" + username + '\'' +
                   ", clientId='" + clientId + '\'' +
                   '}';
        }
    }


    @Validated
    //@ConfigurationProperties(prefix = "resourceserver")
    public static class Server {


        @NotBlank
        private String hostName;

        @Min(1025)
        @Max(65536)
        private int port;

//        @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
//        private String from;


        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }


        @Override
        public String toString() {
            return "Server{" +
                   "hostName='" + hostName + '\'' +
                   ", port=" + port +
                   '}';
        }
    }

}



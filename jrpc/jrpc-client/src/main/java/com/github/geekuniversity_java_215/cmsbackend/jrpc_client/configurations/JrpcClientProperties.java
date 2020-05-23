package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;


@Component("jrpcClientProperties")
@Primary
@Data
public class JrpcClientProperties implements Serializable {

    public static final String API_VERSION = "1.0";

    private Login login;

    private Server authServer;
    private Server resourceServer;

    private String apiURL;

    public JrpcClientProperties(){
        login = new Login();
        authServer = new Server();
        resourceServer = new Server();
    }



    // =========================================================

    @Data
    public static class Login {

        @Length(max = 4, min = 1)
//        private String authMethod;

        private String username;
        private String password;

        private String clientId;
        private String clientSecret;

        private TokenDto accessToken = TokenDto.getNew();
        private TokenDto refreshToken = TokenDto.getNew();

        @Override
        public String toString() {
            return "Credentials{" +
                "username='" + username + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
        }
    }

    // =============================================================================

    @Data
    public static class Server {

        private String hostName;
        private int port;
        @Override
        public String toString() {
            return "Server{" +
                "hostName='" + hostName + '\'' +
                ", port=" + port +
                '}';
        }
//        public void copyFrom(Server other) {
//            hostName = other.hostName;
//            port = other.port;
//        }

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

//    public void copyFrom(JrpcClientProperties from) {
//
//        // SerializationUtils.clone BeanUtils.copyProperties not works or shallow copy
//
//        account.copyFrom(from.getAccount());
//        authServer.copyFrom(from.getAuthServer());
//        resourceServer.copyFrom(from.getResourceServer());
//        build();
//    }

}



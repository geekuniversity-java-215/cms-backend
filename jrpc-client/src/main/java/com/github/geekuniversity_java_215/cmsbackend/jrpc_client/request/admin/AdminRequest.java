package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.admin;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@Slf4j
public class AdminRequest extends AbstractRequest {

    public void revokeToken(String username) {

        // Oauth2.0 authorization
        oauthRequest.authorize();

        String url = String.format("http://%1$s:%2$s/admin/user/revoke_token",
            this.jrpcClientProperties.getAuthServer().getHostName(),
            this.jrpcClientProperties.getAuthServer().getPort());

        ResponseEntity<Void> response = performRequest(url, username, Void.class);

        log.info("{}", response.getStatusCode());
    }

    public ResponseEntity<String> test() {

        oauthRequest.authorize();

        String url = String.format("http://%1$s:%2$s/admin/test",
            this.jrpcClientProperties.getAuthServer().getHostName(),
            this.jrpcClientProperties.getAuthServer().getPort());

        return performRequest(url, null, String.class);
    }

}

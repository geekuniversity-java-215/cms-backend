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

    protected <K, T> ResponseEntity<T> performRequest(String uri, K body, Class<T> returnClass) {

        JrpcClientProperties.Account clientAccount = jrpcClientProperties.getAccount();

        // Oauth2.0 authorization -----------------
        oauthRequest.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + clientAccount.getAccessToken());
        //headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<K> requestEntity = RequestEntity
            .post(URI.create(uri))
            .headers(headers)
            .body(body);

        log.info("REQUEST\n" + requestEntity);

        ResponseEntity<T> response = restTemplate.exchange(requestEntity, returnClass);

        //log.info("HTTP " + response.getStatusCode().toString() + "\n" + response.getBody());

        return response;
    }


    public void revokeToken(String userName) {

        // Oauth2.0 authorization
        oauthRequest.authorize();

        String url = String.format("http://%1$s:%2$s/admin/user/revoke_token",
            this.jrpcClientProperties.getAuthServer().getHostName(),
            this.jrpcClientProperties.getAuthServer().getPort());

        //ObjectNode body = JsonNodeFactory.instance.objectNode();
        //body.put("user", userName);
        ResponseEntity<Void> response = performRequest(url, userName, Void.class);

        log.info("{}", response.getStatusCode());
    }

    public String test() {

        oauthRequest.authorize();

        String url = String.format("http://%1$s:%2$s/admin/test",
            this.jrpcClientProperties.getAuthServer().getHostName(),
            this.jrpcClientProperties.getAuthServer().getPort());

        ResponseEntity<String> response = performRequest(url, null, String.class);

        return response.getBody();
    }

}

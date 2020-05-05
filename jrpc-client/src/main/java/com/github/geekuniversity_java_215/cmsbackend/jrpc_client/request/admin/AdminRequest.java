package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.ClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.OauthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Slf4j
public class AdminRequest extends AbstractRequest {

    protected <K, T> ResponseEntity<T> performRequest(String uri, K body, Class<T> returnClass) {

        ClientProperties.Credentials clientCredentials = clientProperties.getCredentials();

        // Oauth2.0 authorization -----------------
        oauthRequest.authorize();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + clientCredentials.getAccessToken());
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
            this.clientProperties.getAuthServer().getHostName(),
            this.clientProperties.getAuthServer().getPort());

        //ObjectNode body = JsonNodeFactory.instance.objectNode();
        //body.put("user", userName);
        ResponseEntity<Void> response = performRequest(url, userName, Void.class);

        log.info("{}", response.getStatusCode());
    }

    public void test() {

        oauthRequest.authorize();

        String url = String.format("http://%1$s:%2$s/admin/hello",
            this.clientProperties.getAuthServer().getHostName(),
            this.clientProperties.getAuthServer().getPort());

        ResponseEntity<String> response = performRequest(url, null, String.class);

        log.info("{}", response.toString() + "\n" + response.getBody());
    }

}

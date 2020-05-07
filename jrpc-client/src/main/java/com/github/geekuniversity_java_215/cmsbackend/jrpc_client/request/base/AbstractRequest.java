package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.oauth.OauthRequest;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.request.JrpcRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Scope("prototype")
@Slf4j
public abstract class AbstractRequest {

    protected JrpcClientProperties jrpcClientProperties;
    protected RestTemplate restTemplate;
    protected OauthRequest oauthRequest;
    protected ObjectMapper objectMapper;

    @Autowired
    public void setJrpcClientPropertiesDTO2(JrpcClientProperties jrpcClientProperties) {
        this.jrpcClientProperties = jrpcClientProperties;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setOauthRequest(OauthRequest oauthRequest) {
        this.oauthRequest = oauthRequest;
    }


    // --------------------------------------------------------------------

    protected <K, T> ResponseEntity<T> performRequest(String uri,
                                                      K body,
                                                      Class<T> returnClass,
                                                      HttpHeaders headers) {

        JrpcClientProperties.Login clientLogin = jrpcClientProperties.getLogin();

        // Oauth2.0 authorization -----------------
        oauthRequest.authorize();

        if(headers == null) {
            headers = new HttpHeaders();
        }
        headers.add("Authorization", "Bearer " + clientLogin.getAccessToken());

        RequestEntity<K> requestEntity = RequestEntity
            .post(URI.create(uri))
            .headers(headers)
            .body(body);

        log.info("REQUEST\n" + requestEntity);

        //log.info("HTTP " + response.getStatusCode().toString() + "\n" + response.getBody());

        return restTemplate.exchange(requestEntity, returnClass);
    }



    protected <K, T> ResponseEntity<T> performRequest(String uri, K body, Class<T> returnClass) {
        return performRequest(uri, body, returnClass, null);
    }







    protected JsonNode performJrpcRequest(long id, String uri, Object params) {

        JsonNode result;
        JrpcRequest jrpcRequest = new JrpcRequest();
        jrpcRequest.setMethod(uri);
        jrpcRequest.setId(id);
        jrpcRequest.setParams(objectMapper.valueToTree(params));

        String json;
        try {
            json = objectMapper.writeValueAsString(jrpcRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = performRequest(uri, json, String.class, headers);

        try {
            result = objectMapper.readTree(response.getBody()).get("result");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }



}

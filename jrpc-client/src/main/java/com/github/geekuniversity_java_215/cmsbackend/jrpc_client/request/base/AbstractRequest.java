package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
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
    public void setJrpcClientPropertiesDTO2(JrpcClientProperties jrpcClientPropertiesDTO2) {
        this.jrpcClientProperties = jrpcClientPropertiesDTO2;
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



    protected JsonNode performRequest(long id, String uri, Object params) {

        JrpcClientProperties.Account clientAccount = jrpcClientProperties.getAccount();

        // Oauth2.0 authorization
        oauthRequest.authorize();


        JsonNode result;

        // JrpcRequest не был запихнут в @Bean //context.getBean(JrpcRequest.class); поэтому new()
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

        log.debug("REQUEST\n" + json);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + clientAccount.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<String> requestEntity = RequestEntity
                .post(URI.create(jrpcClientProperties.getApiURL()))
                .headers(headers)
                .body(json);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        log.debug("HTTP " + response.getStatusCode().toString() + "\n" + response.getBody());
        try {
            result = objectMapper.readTree(response.getBody()).get("result");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}

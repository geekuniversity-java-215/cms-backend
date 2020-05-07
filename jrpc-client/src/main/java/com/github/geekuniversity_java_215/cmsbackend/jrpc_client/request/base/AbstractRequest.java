package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Scope("prototype")
@Slf4j
public class AbstractRequest {

    protected JrpcClientProperties clientProp;
    protected RestTemplate restTemplate;

    @Autowired
    protected void setClientProp(@Qualifier("jrpcClientProperties")JrpcClientProperties clientProp) {
        this.clientProp = clientProp;
    }
    @Autowired
    protected void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected <K, T> ResponseEntity<T> performRequest(String uri, K body, Class<T> returnClass, HttpHeaders headers) {

        headers = getHeaders(headers);
        
//        if(headers.containsKey(HttpHeaders.AUTHORIZATION) &&
//            !headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        }

        RequestEntity<K> requestEntity = RequestEntity
            .post(URI.create(uri))
            .headers(headers)
            .body(body);

        log.debug("REQUEST\n" + requestEntity);
        ResponseEntity<T> result = restTemplate.exchange(requestEntity, returnClass);
        log.debug("HTTP " + result.getStatusCode().toString() + "\n" + result.getBody());

        return result;
    }

    protected HttpHeaders getHeaders(HttpHeaders headers) {
        return headers == null ? new HttpHeaders() : headers;
    }

}

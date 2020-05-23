package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    protected <K, T> ResponseEntity<T> performRequest(String uri,
                                                      K body,
                                                      Class<T> returnClass,
                                                      HttpHeaders headers,
                                                      HttpMethod httpMethod) {

        headers = getHeaders(headers);
        RequestEntity<K> requestEntity = RequestEntity.method(httpMethod, URI.create(uri)).headers(headers).body(body);

        log.debug("REQUEST : " + requestEntity);
        ResponseEntity<T> result = restTemplate.exchange(requestEntity, returnClass);
        log.debug("RESPONSE: HTTP " + result.getStatusCode().toString() + " " + result.getBody());

        return result;
    }

    protected <K, T> ResponseEntity<T> performRequest(String uri, K body, Class<T> returnClass, HttpHeaders headers) {
        return performRequest(uri, body, returnClass, headers, HttpMethod.POST);
    }

    protected <K, T> ResponseEntity<T> performRequest(String uri, K body, Class<T> returnClass) {
        return performRequest(uri, body, returnClass, null, HttpMethod.POST);
    }

    // ==================================================================================

    protected HttpHeaders getHeaders(HttpHeaders headers) {
        return headers == null ? new HttpHeaders() : headers;
    }

}



//        if(headers.containsKey(HttpHeaders.AUTHORIZATION) &&
//            !headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        }

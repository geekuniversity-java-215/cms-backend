package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
public abstract class AbstractRequestWithOauth extends AbstractRequest {

    private OauthRequest oauthRequest;

    @Autowired
    protected void setOauthRequest(OauthRequest oauthRequest) {
        this.oauthRequest = oauthRequest;
    }

    //    protected JrpcClientProperties jrpcClientProperties;
//    protected RestTemplate restTemplate;
//    protected OauthRequest oauthRequest;
//    protected ObjectMapper objectMapper;
//
//    @Autowired
//    public void setJrpcClientPropertiesDTO2(JrpcClientProperties jrpcClientProperties) {
//        this.jrpcClientProperties = jrpcClientProperties;
//    }
//
//    @Autowired
//    public void setRestTemplate(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Autowired
//    public void setObjectMapper(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Autowired
//    public void setOauthRequest(OauthRequest oauthRequest) {
//        this.oauthRequest = oauthRequest;
//    }


    // --------------------------------------------------------------------

    public <K, T> ResponseEntity<T> performRequest(String uri, K body, Class<T> returnClass, HttpHeaders headers) {

        oauthRequest.authorize();
        headers = getHeaders(headers);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + clientProp.getLogin().getAccessToken());
        return super.performRequest(uri, body, returnClass, headers);
    }


    public <K, T> ResponseEntity<T> performRequest(String uri, K body, Class<T> returnClass) {
        return performRequest(uri, body, returnClass, null);
    }

}

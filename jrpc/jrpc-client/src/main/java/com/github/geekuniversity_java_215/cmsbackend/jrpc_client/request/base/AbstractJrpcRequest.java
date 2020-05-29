package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.request.JrpcRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.pivovarit.function.ThrowingFunction.unchecked;

@Component
public class AbstractJrpcRequest extends AbstractRequestWithOauth {

    private static AtomicLong id = new AtomicLong(1);

    protected ObjectMapper objectMapper;
    @Autowired
    protected void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static long nextId() {
        return id.getAndIncrement();
    }

    protected JsonNode performJrpcRequest(String uri, Object params) {

        JsonNode result;
        JrpcRequest jrpcRequest = new JrpcRequest();
        jrpcRequest.setMethod(uri);
        jrpcRequest.setId(AbstractJrpcRequest.nextId());
        jrpcRequest.setParams(objectMapper.valueToTree(params));

        String json = unchecked((request) -> objectMapper.writeValueAsString(request)).apply(jrpcRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = performRequest(clientProp.getApiURL(), json, String.class, headers);

        try {
            result = objectMapper.readTree(response.getBody()).get("result");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

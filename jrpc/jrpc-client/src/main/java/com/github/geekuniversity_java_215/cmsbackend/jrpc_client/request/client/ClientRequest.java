package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractJrpcRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public ClientDto findById(long id) {
        String uri = HandlerName.client.path + "." + HandlerName.client.findById;
        JsonNode response = performJrpcRequest(uri, id);
        return objectMapper.treeToValue(response, ClientDto.class);
    }

    @SneakyThrows
    public ClientDto findByUsername(String username) {
        String uri = HandlerName.client.path + "." + HandlerName.client.findByUsername;
        JsonNode response = performJrpcRequest(uri, username);
        return objectMapper.treeToValue(response, ClientDto.class);
    }

    @SneakyThrows
    public ClientDto findByUser(UserDto user) {
        String uri = HandlerName.client.path + "." + HandlerName.client.findByUsername;
        JsonNode response = performJrpcRequest(uri, user.getUsername());
        return objectMapper.treeToValue(response, ClientDto.class);
    }

    @SneakyThrows
    public ClientDto getCurrent() {
        String uri = HandlerName.client.path + "." + HandlerName.client.getCurrent;
        JsonNode response = performJrpcRequest(uri, null);
        return objectMapper.treeToValue(response, ClientDto.class);
    }

    @SneakyThrows
    public Long save(ClientDto client) {
        String uri = HandlerName.client.path + "." + HandlerName.client.save;
        JsonNode response = performJrpcRequest(uri, client);
        return objectMapper.treeToValue(response, Long.class);
    }
}

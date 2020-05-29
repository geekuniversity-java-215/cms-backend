package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractJrpcRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRequest extends AbstractJrpcRequest {

    public UserDto findByUsername(String username) throws JsonProcessingException {
        String uri = HandlerName.user.path + "." + HandlerName.user.findByUsername;
        JsonNode response = performJrpcRequest(uri, username);
        return objectMapper.treeToValue(response, UserDto.class);
    }

    public UserDto findById(long id) throws JsonProcessingException {
        String uri = HandlerName.user.path + "." + HandlerName.user.findById;
        JsonNode response = performJrpcRequest(uri, id);
        return objectMapper.treeToValue(response, UserDto.class);
    }

    public Long save(UserDto user) throws JsonProcessingException {
        String uri = HandlerName.user.path + "." + HandlerName.user.save;
        JsonNode response = performJrpcRequest(uri, user);
        return objectMapper.treeToValue(response, Long.class);
    }

    public void makeCourier(UserDto user) {
        String uri = HandlerName.user.path + "." + HandlerName.user.makeCourier;
        performJrpcRequest(uri, user.getId());
    }

    public void makeClient(UserDto user) {
        String uri = HandlerName.user.path + "." + HandlerName.user.makeClient;
        performJrpcRequest(uri, user.getId());
    }


}

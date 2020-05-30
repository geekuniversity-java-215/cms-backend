package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.courier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractJrpcRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CourierRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public CourierDto findById(long id) {
        String uri = HandlerName.courier.path + "." + HandlerName.courier.findById;
        JsonNode response = performJrpcRequest(uri, id);
        return objectMapper.treeToValue(response, CourierDto.class);
    }

    @SneakyThrows
    public CourierDto findByUsername(String username) {
        String uri = HandlerName.courier.path + "." + HandlerName.courier.findByUsername;
        JsonNode response = performJrpcRequest(uri, username);
        return objectMapper.treeToValue(response, CourierDto.class);
    }

    @SneakyThrows
    public CourierDto findByUser(UserDto user) {
        String uri = HandlerName.courier.path + "." + HandlerName.courier.findByUsername;
        JsonNode response = performJrpcRequest(uri, user.getUsername());
        return objectMapper.treeToValue(response, CourierDto.class);
    }

    @SneakyThrows
    public CourierDto getCurrent() {
        String uri = HandlerName.courier.path + "." + HandlerName.courier.getCurrent;
        JsonNode response = performJrpcRequest(uri, null);
        return objectMapper.treeToValue(response, CourierDto.class);
    }

    @SneakyThrows
    public Long save(CourierDto courier) {
        String uri = HandlerName.courier.path + "." + HandlerName.courier.save;
        JsonNode response = performJrpcRequest(uri, courier);
        return objectMapper.treeToValue(response, Long.class);
    }
}

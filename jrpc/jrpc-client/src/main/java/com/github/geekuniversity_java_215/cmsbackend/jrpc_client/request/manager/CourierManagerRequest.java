package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractJrpcRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CourierManagerRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public CourierDto findById(long id) {
        String uri = HandlerName.manager.courier.path + "." + HandlerName.manager.courier.findById;
        JsonNode response = performJrpcRequest(uri, id);
        return objectMapper.treeToValue(response, CourierDto.class);
    }

    @SneakyThrows
    public CourierDto findByUsername(String username) {
        String uri = HandlerName.manager.courier.path + "." + HandlerName.manager.courier.findByUsername;
        JsonNode response = performJrpcRequest(uri, username);
        return objectMapper.treeToValue(response, CourierDto.class);
    }

    @SneakyThrows
    public CourierDto findByUser(UserDto user) {
        String uri = HandlerName.manager.courier.path + "." + HandlerName.manager.courier.findByUsername;
        JsonNode response = performJrpcRequest(uri, user.getUsername());
        return objectMapper.treeToValue(response, CourierDto.class);
    }

    @SneakyThrows
    public Long save(CourierDto courier) {
        String uri = HandlerName.manager.courier.path + "." + HandlerName.manager.courier.save;
        JsonNode response = performJrpcRequest(uri, courier);
        return objectMapper.treeToValue(response, Long.class);
    }
}

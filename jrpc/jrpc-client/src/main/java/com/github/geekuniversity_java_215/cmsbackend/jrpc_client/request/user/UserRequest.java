package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractJrpcRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRequest extends AbstractJrpcRequest {

    public JsonNode save(UserDto user) {

        String uri = HandlerName.user.path + "." + HandlerName.user.save;
        long id = AbstractJrpcRequest.nextId();
        return performJrpcRequest(id, uri, user);
    }
}

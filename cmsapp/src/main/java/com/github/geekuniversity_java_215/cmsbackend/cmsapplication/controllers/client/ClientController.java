package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.client.ClientConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
import org.springframework.security.access.annotation.Secured;


// ToDo: перенести это все в управлялку ManagerClientController, кроме последнего метода

/**
 * Client management
 */
@JrpcController(HandlerName.client.path)
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;
    private final ClientConverter converter;

    public ClientController(ClientService clientService, UserService userService,
                            ClientConverter converter) {
        this.clientService = clientService;
        this.userService = userService;
        this.converter = converter;
    }



    @JrpcMethod(HandlerName.client.getCurrent)
    @Secured(UserRole.VAL.CLIENT)
    public JsonNode getCurrent(JsonNode params) {

        User user = userService.getCurrent();
        Client client = clientService.findOneByUser(user).orElse(null);
        return converter.toDtoJson(client);
    }


}

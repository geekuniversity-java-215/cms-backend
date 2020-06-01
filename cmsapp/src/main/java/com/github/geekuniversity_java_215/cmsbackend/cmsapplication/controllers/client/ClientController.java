package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.client.ClientConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



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
    @Secured(UserRole.CLIENT)
    public JsonNode getCurrent(JsonNode params) {

        User user = userService.getCurrentUser();
        Client client = clientService.findOneByUser(user).orElse(null);
        return converter.toDtoJson(client);
    }


}

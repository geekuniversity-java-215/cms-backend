package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.manager;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@JrpcController(HandlerName.manager.client.path)
@Secured(UserRole.VAL.MANAGER)
public class ClientManagerController {


    private final ClientService clientService;
    private final UserService userService;
    private final ClientConverter converter;

    public ClientManagerController(ClientService clientService, UserService userService,
                                    ClientConverter converter) {
        this.clientService = clientService;
        this.userService = userService;
        this.converter = converter;
    }

    @JrpcMethod(HandlerName.manager.client.findByUsername)
    public JsonNode findByUsername(JsonNode params) {

        String username = converter.get(params, String.class);
        Client client = clientService.findByUsername(username).orElse(null);;
        return converter.toDtoJson(client);
    }

    @JrpcMethod(HandlerName.manager.client.save)
    public JsonNode save(JsonNode params) {

        Client client = converter.toEntity(params);
        Long clientId = client.getId();

        // check client have user
        if(client.getUser() == null) {
            throw new IllegalArgumentException("Client without user");
        }
        long userId = client.getUser().getId();

        // check client user exists
        User user = userService.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User by id " + userId + " not found"));

        // check user not stolen from other client
        clientService.findOneByUser(user).ifPresent(c -> {
            if (clientId != null && !c.getId().equals(clientId))
                throw new IllegalArgumentException("Stealing user: " + user.getUsername() + " from another client: " + c.getId());
        });

        // assign CLIENT role
        user.getRoles().add(UserRole.CLIENT);
        userService.save(user);
        client = clientService.save(client);

        return converter.toIdJson(client);
    }


}

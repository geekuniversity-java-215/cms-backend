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
    private final UserRoleService userRoleService;

    public ClientController(ClientService clientService, UserService userService,
                            ClientConverter converter, UserRoleService userRoleService) {
        this.clientService = clientService;
        this.userService = userService;
        this.converter = converter;
        this.userRoleService = userRoleService;
    }


    @JrpcMethod(HandlerName.client.findByUsername)
    @Secured(UserRole.MANAGER)
    public JsonNode findByUsername(JsonNode params) {

        String username = converter.get(params, String.class);
        Client client = clientService.findByUsername(username).orElse(null);;
        return converter.toDtoJson(client);
    }



    @JrpcMethod(HandlerName.client.save)
    @Secured(UserRole.MANAGER)
    public JsonNode save(JsonNode params) {

        Client client = converter.toEntity(params);
        Long clientId = client.getId();
        if(client.getUser() == null) {
            throw new IllegalArgumentException("Client without user");
        }

        long userId = client.getUser().getId();
        User user = userService.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User by id " + userId + " not found"));

        clientService.findOneByUser(user).ifPresent(c -> {
            if (clientId != null && !c.getId().equals(clientId))
            throw new IllegalArgumentException("Stealing user: " + user.getUsername() + " from another client: " + c.getId());
        });

        //noinspection OptionalGetWithoutIsPresent
        user.getRoles().add(userRoleService.findByName(UserRole.CLIENT).get());
        userService.save(user);
        client = clientService.save(client);

        return converter.toIdJson(client);
    }










    // ====================================================================================

    @JrpcMethod(HandlerName.client.getCurrent)
    @Secured(UserRole.CLIENT)
    public JsonNode getCurrent(JsonNode params) {

        //noinspection OptionalGetWithoutIsPresent
        User user = userService.getCurrentUser().get();
        Client client = clientService.findOneByUser(user).orElse(null);
        return converter.toDtoJson(client);
    }


}

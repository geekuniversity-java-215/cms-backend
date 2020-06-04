package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.user;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;


// ToDo: перенести это все в управлялку ManagerUserController, кроме последнего метод

/**
 * User management
 */
@JrpcController(HandlerName.user.path)
@Secured(UserRole.USER)
public class UserController {

    private final UserService userService;
    private final UserConverter converter;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserConverter converter, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }

    @JrpcMethod(HandlerName.user.getCurrent)
    public JsonNode getCurrent(JsonNode params) {

        User user = userService.getCurrent();
        return converter.toDtoJson(user);
    }

    /**
     * Update user data
     * @param params User
     * @return
     */
    @JrpcMethod(HandlerName.user.save)
    public JsonNode save(JsonNode params) {

        User user = converter.toEntity(params);

        // Check that userDto have same username, mail, phone
        if(!user.equals(userService.getCurrent())) {
            throw new IllegalArgumentException("Invalid user params. Username, mail, phone should be the same");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.save(user);
        return converter.toIdJson(user);
    }

}

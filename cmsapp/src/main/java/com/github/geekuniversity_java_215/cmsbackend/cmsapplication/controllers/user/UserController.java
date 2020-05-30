package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.user;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.user.UserSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserSpecDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;


// ToDo: перенести это все в управлялку ManagerUserController, кроме последнего метод

/**
 * User management
 */
@JrpcController(HandlerName.user.path)
public class UserController {

    //private final ClientService clientService;
    //private final CourierService courierService;

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserConverter converter;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRoleService userRoleService,
                          UserConverter converter, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }


    @JrpcMethod(HandlerName.user.findById)
    @Secured(UserRole.MANAGER)
    public JsonNode findById(JsonNode params) {

        Long id = converter.get(params, Long.class);
        User user = userService.findById(id).orElse(null);
        return converter.toDtoJson(user);
    }


    /**
     * Get List<User> by idList
     * @param params List<Long> idList
     * @return
     */
    @JrpcMethod(HandlerName.user.findAllById)
    @Secured(UserRole.MANAGER)
    public JsonNode findAllById(JsonNode params) {

        List<Long> idList = converter.getList(params, Long.class);
        List<User> list = userService.findAllById(idList);
        return converter.toDtoListJson(list);
    }


    /**
     * Get user by Specification
     * @param params UserSpecDto
     * @return
     */
    @JrpcMethod(HandlerName.user.findByUsername)
    @Secured(UserRole.MANAGER)
    public JsonNode findByUsername(JsonNode params) {

        String username = converter.get(params, String.class);
        return converter.toDtoJson(userService.findByUsername(username).orElse(null));
    }



    /**
     * Get user by Specification
     * @param params UserSpecDto
     * @return
     */
    @JrpcMethod(HandlerName.user.findAll)
    @Secured(UserRole.MANAGER)
    public JsonNode findAll(JsonNode params) {

        Optional<UserSpecDto> specDto = converter.toSpecDto(params);
        Specification<User> spec =  UserSpecBuilder.build(specDto.orElse(null));
        return converter.toDtoListJson(userService.findAll(spec));
    }

    //
    //  Return first ProductSpecDto.limit elements
    //

    /***
     * Get first limit elements by UserSpecDto (with ~pagination)
     * @return
     */
    @JrpcMethod(HandlerName.order.manager.findFirst)
    @Secured(UserRole.MANAGER)
    public JsonNode findFirst(JsonNode params) {

        Optional<UserSpecDto> specDto = converter.toSpecDto(params);
        Specification<User> spec = UserSpecBuilder.build(specDto.orElse(null));
        int limit = specDto.map(UserSpecDto::getLimit).orElse(1);
        Page<User> page = userService.findAll(spec, PageRequest.of(0, limit));
        return converter.toDtoListJson(page.toList());
    }


    /**
     * Save User(insert new or update existing)
     * @param params User
     * @return
     */
    @JrpcMethod(HandlerName.user.save)
    @Secured(UserRole.MANAGER)
    public JsonNode save(JsonNode params) {

        User user = converter.toEntity(params);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.save(user);
        return converter.toIdJson(user);
    }


    /**
     * Delete User
     * @param params
     * @return
     */
    @JrpcMethod(HandlerName.user.delete)
    @Secured(UserRole.MANAGER)
    public JsonNode delete(JsonNode params) {

        User user = converter.toEntity(params);
        userService.delete(user);
        return null;
    }

    // -------------------------------------------------------------------------------

    @JrpcMethod(HandlerName.user.getCurrent)
    @Secured(UserRole.USER)
    public JsonNode getCurrent(JsonNode params) {

        //noinspection OptionalGetWithoutIsPresent
        User user = userService.getCurrentUser().get();
        return converter.toDtoJson(user);
    }

}

package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.user;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.user.UserSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserSpecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Optional;


/**
 * User management
 */
@JrpcController(HandlerName.user.path)
@Secured(UserRole.MANAGER)
public class UserController {

    private final UserService userService;
    private final UserConverter converter;

    @Autowired
    public UserController(UserService userService, UserConverter converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @JrpcMethod(HandlerName.user.findById)
    public JsonNode findById(JsonNode params) {

        Long id = converter.getId(params);
        User user = userService.findById(id).orElse(null);
        return converter.toDtoJson(user);
    }


    /**
     * Get List<User> by idList
     * @param params List<Long> idList
     * @return
     */
    @JrpcMethod(HandlerName.user.findAllById)
    public JsonNode findAllById(JsonNode params) {

        List<Long> idList = converter.getIdList(params);
        List<User> list = userService.findAllById(idList);
        return converter.toDtoListJson(list);
    }

    /**
     * Get user by Specification
     * @param params UserSpecDto
     * @return
     */
    @JrpcMethod(HandlerName.user.findAll)
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
    public JsonNode save(JsonNode params) {

        User user = converter.toEntity(params);
        user = userService.save(user);
        return converter.toIdJson(user);
    }


    /**
     * Delete User
     * @param params
     * @return
     */
    @JrpcMethod(HandlerName.user.delete)
    public JsonNode delete(JsonNode params) {

        User user = converter.toEntity(params);
        userService.delete(user);
        return null;
    }


}

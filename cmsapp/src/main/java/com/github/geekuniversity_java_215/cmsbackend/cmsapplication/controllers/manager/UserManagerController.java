package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.manager;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.user.UserSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserSpecDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@JrpcController(HandlerName.manager.user.path)
@Secured(UserRole.MANAGER)
public class UserManagerController {


    private final UserService userService;
    private final UserConverter converter;
    private final PasswordEncoder passwordEncoder;

    public UserManagerController(UserService userService, UserConverter converter,
                                 PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }


    @JrpcMethod(HandlerName.manager.user.findById)
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
    @JrpcMethod(HandlerName.manager.user.findAllById)
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
    @JrpcMethod(HandlerName.manager.user.findByUsername)
    public JsonNode findByUsername(JsonNode params) {

        String username = converter.get(params, String.class);
        return converter.toDtoJson(userService.findByUsername(username).orElse(null));
    }



    /**
     * Get user by Specification
     * @param params UserSpecDto
     * @return
     */
    @JrpcMethod(HandlerName.manager.user.findAll)
    public JsonNode findAll(JsonNode params) {

        UserSpecDto specDto = converter.toSpecDto(params);
        Specification<User> spec =  UserSpecBuilder.build(specDto);
        return converter.toDtoListJson(userService.findAll(spec));
    }

    //
    //  Return first ProductSpecDto.limit elements
    //

    /***
     * Get first limit elements by UserSpecDto (with ~pagination)
     * @return
     */
    @JrpcMethod(HandlerName.manager.user.findFirst)
    public JsonNode findFirst(JsonNode params) {

        UserSpecDto specDto = converter.toSpecDto(params);
        int limit = specDto != null ? specDto.getLimit() : 1;
        Specification<User> spec = UserSpecBuilder.build(specDto);
        Page<User> page = userService.findAll(spec, PageRequest.of(0, limit));
        return converter.toDtoListJson(page.toList());
    }


    /**
     * Save User(insert new or update existing)
     * @param params User
     * @return
     */
    @JrpcMethod(HandlerName.manager.user.save)
    public JsonNode save(JsonNode params) {

        User user = converter.toEntity(params);

        // convert password from plain text to bcrypt
        if (user.getPassword() != null &&
            !user.getPassword().contains("{bcrypt}")) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user = userService.save(user);
        return converter.toIdJson(user);
    }


    /**
     * Delete User
     * @param params
     * @return
     */
    @JrpcMethod(HandlerName.manager.user.delete)
    public JsonNode delete(JsonNode params) {

        User user = converter.toEntity(params);
        userService.delete(user);
        return null;
    }

}

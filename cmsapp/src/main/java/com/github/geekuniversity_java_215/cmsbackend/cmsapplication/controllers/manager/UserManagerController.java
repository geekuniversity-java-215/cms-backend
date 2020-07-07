package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.manager;


import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.user.UserSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@JrpcController(HandlerName.manager.user.path)
@Secured(UserRole.VAL.MANAGER)
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
    public UserDto findById(Long id) {

        User user = userService.findById(id).orElse(null);
        return converter.toDto(user);
    }


    /**
     * Get List<User> by idList
     * @param params List<Long> idList
     * @return List<UserDto>
     */
    @JrpcMethod(HandlerName.manager.user.findAllById)
    public List<UserDto> findAllById(List<Long> idList) {

        List<User> list = userService.findAllById(idList);
        return converter.toDtoList(list);
    }


    /**
     * Find user by username
     * @param String username
     * @return UserDto
     */
    @JrpcMethod(HandlerName.manager.user.findByUsername)
    public UserDto findByUsername(String username) {

        User user = userService.findByUsername(username).orElse(null);
        return converter.toDto(user);
    }



    /**
     * Find all users
     * @param params UserSpecDto
     * @return List<UserDto>
     */
    @JrpcMethod(HandlerName.manager.user.findAll)
    public List<UserDto> findAll(UserSpecDto specDto) {

        Specification<User> spec =  converter.buildSpec(specDto);
        return converter.toDtoList(userService.findAll(spec));
    }



    /***
     * Get first limit elements by UserSpecDto (with ~pagination)
     * @return List<UserDto>
     */
    @JrpcMethod(HandlerName.manager.user.findFirst)
    public List<UserDto> findFirst(UserSpecDto specDto) {

        int limit = specDto != null ? specDto.getLimit() : 1;
        Specification<User> spec =  converter.buildSpec(specDto);
        Page<User> page = userService.findAll(spec, PageRequest.of(0, limit));
        return converter.toDtoList(page.toList());
    }


    /**
     * Save User, may change roles
     * <br> Will not save Account
     * @param params userDto
     * @return
     */
    @JrpcMethod(HandlerName.manager.user.save)
    public Long save(UserDto userDto) {

        User user = converter.toEntity(userDto);

        // update/set password - if not empty and not loaded from DB
        if (!StringUtils.isBlank(user.getPassword()) &&
            !user.getPassword().contains("{bcrypt}")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Security
        User currentUser = userService.getCurrent();
        // preserve Account
        user.setAccount(currentUser.getAccount());

        user = userService.save(user);
        return user.getId();
    }


    /**
     * Delete User
     * @param params
     * @return
     */
    @JrpcMethod(HandlerName.manager.user.delete)
    public void delete(UserDto userDto) {

        User user = converter.toEntity(userDto);
        userService.delete(user);
    }

}

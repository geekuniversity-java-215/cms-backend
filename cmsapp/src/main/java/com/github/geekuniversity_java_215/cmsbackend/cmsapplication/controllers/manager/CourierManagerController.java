package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.courier.CourierConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@JrpcController(HandlerName.manager.courier.path)
@Secured(UserRole.MANAGER)
public class CourierManagerController {

    private final CourierService courierService;
    private final UserService userService;
    private final CourierConverter converter;
    private final UserRoleService userRoleService;

    public CourierManagerController(CourierService courierService, UserService userService,
                                    CourierConverter converter, UserRoleService userRoleService) {
        this.courierService = courierService;
        this.userService = userService;
        this.converter = converter;
        this.userRoleService = userRoleService;
    }


    @JrpcMethod(HandlerName.manager.courier.findByUsername)
    public JsonNode findByUsername(JsonNode params) {

        String username = converter.get(params, String.class);
        Courier courier = courierService.findByUsername(username).orElse(null);;
        return converter.toDtoJson(courier);
    }


    @JrpcMethod(HandlerName.manager.courier.save)
    public JsonNode save(JsonNode params) {


        Courier courier = converter.toEntity(params);
        Long courierId = courier.getId();
        long userId = courier.getUser().getId();

        // check courier have user
        if(courier.getUser() == null) {
            throw new IllegalArgumentException("Courier without user");
        }

        // check courier user exists
        User user = userService.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User by id " + userId + " not found"));

        // check user not stealed from other courier
        courierService.findOneByUser(user).ifPresent(c -> {
            if (courierId!= null && !c.getId().equals(courierId))
                throw new IllegalArgumentException("Stealing user: " + user.getUsername() + " from another courier: " + c.getId());
        });


        // assign COURIER role
        //noinspection OptionalGetWithoutIsPresent
        user.getRoles().add(userRoleService.findByName(UserRole.COURIER).get());
        userService.save(user);
        courier = courierService.save(courier);

        return converter.toIdJson(courier);
    }
}

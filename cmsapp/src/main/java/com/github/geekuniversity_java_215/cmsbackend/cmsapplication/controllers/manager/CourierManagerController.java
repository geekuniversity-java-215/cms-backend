package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.courier.CourierConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@JrpcController(HandlerName.manager.courier.path)
@Secured(UserRole.VAL.MANAGER)
public class CourierManagerController {

    private final CourierService courierService;
    private final UserService userService;
    private final CourierConverter converter;

    public CourierManagerController(CourierService courierService, UserService userService,
                                    CourierConverter converter) {
        this.courierService = courierService;
        this.userService = userService;
        this.converter = converter;
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

        // check courier have user
        if(courier.getUser() == null) {
            throw new IllegalArgumentException("Courier without user");
        }
        long userId = courier.getUser().getId();

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
        user.getRoles().add(UserRole.COURIER);
        userService.save(user);
        courier = courierService.save(courier);

        return converter.toIdJson(courier);
    }
}

package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.courier;

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


// ToDo: перенести это все в управлялку ManagerCourierController, кроме последнего метода
/**
 * Courier management
 */
@JrpcController(HandlerName.courier.path)
public class CourierController {


    private final CourierService courierService;
    private final UserService userService;
    private final CourierConverter converter;
    private final UserRoleService userRoleService;

    public CourierController(CourierService courierService, UserService userService, CourierConverter converter, UserRoleService userRoleService) {
        this.courierService = courierService;
        this.userService = userService;
        this.converter = converter;
        this.userRoleService = userRoleService;
    }

    @JrpcMethod(HandlerName.courier.findByUsername)
    @Secured(UserRole.MANAGER)
    public JsonNode findByUsername(JsonNode params) {

        String username = converter.get(params, String.class);
        Courier courier = courierService.findByUsername(username).orElse(null);;
        return converter.toDtoJson(courier);
    }


    @JrpcMethod(HandlerName.courier.save)
    @Secured(UserRole.MANAGER)
    public JsonNode save(JsonNode params) {


        Courier courier = converter.toEntity(params);
        Long courierId = courier.getId();
        if(courier.getUser() == null) {
            throw new IllegalArgumentException("Courier without user");
        }

        long userId = courier.getUser().getId();
        User user = userService.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User by id " + userId + " not found"));

        courierService.findOneByUser(user).ifPresent(c -> {
            if (courierId!= null && !c.getId().equals(courierId))
                throw new IllegalArgumentException("Stealing user: " + user.getUsername() + " from another courier: " + c.getId());
        });


        //noinspection OptionalGetWithoutIsPresent
        user.getRoles().add(userRoleService.findByName(UserRole.COURIER).get());
        userService.save(user);
        courier = courierService.save(courier);

        return converter.toIdJson(courier);
    }



    // ==================================================================================

    @JrpcMethod(HandlerName.courier.getCurrent)
    @Secured(UserRole.COURIER)
    public JsonNode getCurrent(JsonNode params) {

        //noinspection OptionalGetWithoutIsPresent
        User user = userService.getCurrentUser().get();
        Courier client = courierService.findOneByUser(user).orElse(null);
        return converter.toDtoJson(client);
    }


}

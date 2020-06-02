package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.courier;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.courier.CourierConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import org.springframework.security.access.annotation.Secured;



// ToDo: перенести это все в управлялку ManagerCourierController, кроме последнего метода
/**
 * Courier management
 */
@JrpcController(HandlerName.courier.path)
public class CourierController {

    private final CourierService courierService;
    private final UserService userService;
    private final CourierConverter converter;

    public CourierController(CourierService courierService, UserService userService, CourierConverter converter) {
        this.courierService = courierService;
        this.userService = userService;
        this.converter = converter;
    }


    @JrpcMethod(HandlerName.courier.getCurrent)
    @Secured(UserRole.COURIER)
    public JsonNode getCurrent(JsonNode params) {

        User user = userService.getCurrentUser();
        Courier client = courierService.findOneByUser(user).orElse(null);
        return converter.toDtoJson(client);
    }


}

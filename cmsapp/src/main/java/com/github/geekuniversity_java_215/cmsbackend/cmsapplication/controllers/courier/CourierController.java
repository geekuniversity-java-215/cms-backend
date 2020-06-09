package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.courier;

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


    /**
     * Return current courier
     * @param params null
     * @return CourierDto
     */
    @JrpcMethod(HandlerName.courier.getCurrent)
    @Secured(UserRole.VAL.COURIER)
    public JsonNode getCurrent(JsonNode params) {

        User user = userService.getCurrent();
        Courier client = courierService.findOneByUser(user).orElse(null);
        return converter.toDtoJson(client);
    }


}

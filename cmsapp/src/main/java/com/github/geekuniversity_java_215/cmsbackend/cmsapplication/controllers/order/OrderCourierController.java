package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.order;

import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@JrpcController(HandlerName.order_courier.path)
@Secured(UserRole.MANAGER)
public class OrderCourierController {

    private final OrderService orderService;
    private final OrderConverter converter;

    @Autowired
    public OrderCourierController(OrderService orderService, OrderConverter converter) {
        this.orderService = orderService;
        this.converter = converter;
    }



    // ==================================================================================


    private void checkIsMyOrder(Order order) {

    }

}

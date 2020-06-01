package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.client.ClientConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.order.OrderSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;

import java.util.concurrent.atomic.AtomicReference;


@JrpcController(HandlerName.order.client.path)
@Secured(UserRole.CLIENT)
public class OrderClientController {


    private final OrderService orderService;
    private final OrderConverter converter;
    private final ClientConverter clientConverter;

    private final UserService userService;
    private final ClientService clientService;

    public OrderClientController(OrderService orderService, OrderConverter converter,
                                 ClientConverter clientConverter, UserService userService,
                                 ClientService clientService) {
        this.orderService = orderService;
        this.converter = converter;
        this.clientConverter = clientConverter;
        this.userService = userService;
        this.clientService = clientService;
    }


    // Will show only all mine orders
    @JrpcMethod(HandlerName.order.client.findAll)
    public JsonNode findAll(JsonNode params) {

        OrderSpecDto specDto = filterOrderShowOnlyMine(params);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto);
        return converter.toDtoListJson(orderService.findAll(spec));
    }


    // ToDo: если клиент нажмет кнопку сохранить еще раз - затрет существующий заказ
    //  Фиксить пересохранение существующего заказа
    @JrpcMethod(HandlerName.order.client.save)
    public JsonNode save(JsonNode params) {

        Order order = converter.toEntity(params);
        order.setClient(getCurrentClient());
        order = orderService.save(order);
        return converter.toIdJson(order);
    }


    // ==================================================================================




    private Client getCurrentClient() {

        AtomicReference<Client> result = new AtomicReference<>();
        clientService.findOneByUser(userService.getCurrentUser()).ifPresent(result::set);
        return result.get();
    }


    private OrderSpecDto filterOrderShowOnlyMine(JsonNode params) {

        OrderSpecDto result;
        AtomicReference<OrderSpecDto> specDtoAtom = new AtomicReference<>();
        converter.toSpecDto(params).ifPresent(specDtoAtom::set);
        result = specDtoAtom.get();

        // assign current client to OrderSpecDto
        if(result == null) {
            result = new OrderSpecDto();
        }
        ClientDto clientDto = clientConverter.toDto(getCurrentClient());
        result.setClient(clientDto);

        return result;
    }
}

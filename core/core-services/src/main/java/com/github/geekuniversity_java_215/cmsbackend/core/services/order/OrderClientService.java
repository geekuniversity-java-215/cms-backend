package com.github.geekuniversity_java_215.cmsbackend.core.services.order;

import com.github.geekuniversity_java_215.cmsbackend.core.converters.client.ClientConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.order.OrderSpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
// @SuppressWarnings("DuplicatedCode")
public class OrderClientService {

    private final OrderService orderService;
    private final ClientService clientService;
    private final ClientConverter clientConverter;

    public OrderClientService(OrderService orderService, ClientService clientService,
                              ClientConverter clientConverter) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.clientConverter = clientConverter;
    }



    public Optional<Order> findById(Long id) {

        OrderSpecDto specDto = filterOrderShowOnlyMine(null);
        specDto.setId(id);
        Specification<Order> spec = OrderSpecBuilder.build(specDto);
        return orderService.findOne(spec);
    }

    public List<Order> findAll(OrderSpecDto specDto) {

        specDto = filterOrderShowOnlyMine(specDto);
        Specification<Order> spec =  OrderSpecBuilder.build(specDto);
        return orderService.findAll(spec);
    }

    public Order save(Order order) {

        order.setClient(clientService.getCurrent());
        checkOrderOwner(order);
        return orderService.save(order);
    }

    public Order cancel(Long id) {

        checkOrderOwner(id);

        throw new NotImplementedException();
    }



    // ==============================================================================


    private OrderSpecDto filterOrderShowOnlyMine(OrderSpecDto specDto) {

        OrderSpecDto result = specDto;
        // assign current courier to OrderSpecDto
        if(result == null) {
            result = new OrderSpecDto();
        }
        ClientDto clientDto = clientConverter.toDto(clientService.getCurrent());
        result.setClient(clientDto);
        return result;
    }

    /**
     * Check that currently being modified Order is belong to current Client
     * @param order
     */
    private void checkOrderOwner(Order order) {

        if(order != null &&  order.getId() != null) {
            Order old = orderService.findById(order.getId()).orElseThrow(() ->
                new IllegalArgumentException("Order with id:\n" + order.getId() + " not exists"));

            if (!old.getClient().equals(clientService.getCurrent())) {
                throw new AccessDeniedException("HAAKCEEER !!!");
            }
        }
    }

    private void checkOrderOwner(Long orderId) {
        Order order = orderService.findByIdOrError(orderId);
        checkOrderOwner(order);
    }
}

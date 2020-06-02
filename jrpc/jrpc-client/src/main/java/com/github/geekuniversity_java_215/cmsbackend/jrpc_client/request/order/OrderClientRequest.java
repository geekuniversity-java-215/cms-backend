package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractJrpcRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class OrderClientRequest extends AbstractJrpcRequest {


    @SneakyThrows
    public OrderDto findById(long id) {
        String uri = HandlerName.order.client.path + "." + HandlerName.order.client.findById;
        JsonNode response = performJrpcRequest(uri, id);
        return objectMapper.treeToValue(response, OrderDto.class);
    }

    @SneakyThrows
    public List<OrderDto> findAll(OrderSpecDto spec) {
        String uri = HandlerName.order.client.path + "." + HandlerName.order.client.findAll;
        JsonNode response = performJrpcRequest(uri, spec);
        return Arrays.asList(objectMapper.treeToValue(response, OrderDto[].class));
    }

    @SneakyThrows
    public Long save(OrderDto order) {
        String uri = HandlerName.order.client.path + "." + HandlerName.order.client.save;
        JsonNode response = performJrpcRequest(uri, order);
        return objectMapper.treeToValue(response, Long.class);
    }
}

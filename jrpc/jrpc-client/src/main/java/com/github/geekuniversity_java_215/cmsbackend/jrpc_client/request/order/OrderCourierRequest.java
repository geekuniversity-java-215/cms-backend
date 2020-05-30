package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.order;

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
public class OrderCourierRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public List<OrderDto> findAll(OrderSpecDto spec) {
        String uri = HandlerName.order.courier.path + "." + HandlerName.order.courier.findAll;
        JsonNode response = performJrpcRequest(uri, spec);
        return Arrays.asList(objectMapper.treeToValue(response, OrderDto[].class));
    }

    @SneakyThrows
    public List<OrderDto> findNew(OrderSpecDto spec) {
        String uri = HandlerName.order.courier.path + "." + HandlerName.order.courier.findNew;
        JsonNode response = performJrpcRequest(uri, spec);
        return Arrays.asList(objectMapper.treeToValue(response, OrderDto[].class));
    }
}

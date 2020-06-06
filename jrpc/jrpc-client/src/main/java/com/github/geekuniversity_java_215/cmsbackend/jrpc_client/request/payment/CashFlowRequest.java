package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.payment;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractJrpcRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowSpecDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CashFlowRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public List<CashFlowDto> findByUserAndDate(CashFlowSpecDto spec) {
        String uri = HandlerName.payment.path + "." +HandlerName.payment.requestForCashFlows;
        JsonNode response = performJrpcRequest(uri, spec);
        return Arrays.asList(objectMapper.treeToValue(response, CashFlowDto[].class));
    }
}
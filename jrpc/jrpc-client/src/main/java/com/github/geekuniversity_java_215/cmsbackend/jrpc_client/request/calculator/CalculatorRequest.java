package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.calculator;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractJrpcRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.calculator.CalculatorParams;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.calculator.CalculatorParamsZoper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

@Component
public class CalculatorRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public double add(double a, double b) {
        String uri = HandlerName.calculator.path + "." + HandlerName.calculator.add;
        JsonNode response = performJrpcRequest(uri, new CalculatorParams(a, b));
        return objectMapper.treeToValue(response, Double.class);
    }

    @SneakyThrows
    public double sub(double a, double b) {
        String uri = HandlerName.calculator.path + "." + HandlerName.calculator.sub;
        JsonNode response = performJrpcRequest(uri, new CalculatorParams(a, b));
        return objectMapper.treeToValue(response, Double.class);
    }


    @SneakyThrows
    public List<Double> ssuper(List<Double> list) {
        String uri = HandlerName.calculator.path + "." + HandlerName.calculator.ssuper;
        JsonNode response = performJrpcRequest(uri, list);

        Class<?> tArrayClass = Array.newInstance(Double.class, 0).getClass();
        return Arrays.asList((Double[])objectMapper.treeToValue(response, tArrayClass));

    }

    @SneakyThrows
    public List<Double> zoper(double l0, List<Double> list) {
        String uri = HandlerName.calculator.path + "." + HandlerName.calculator.zoper;
        JsonNode response = performJrpcRequest(uri, new CalculatorParamsZoper(l0, list));

        Class<?> tArrayClass = Array.newInstance(Double.class, 0).getClass();
        return Arrays.asList((Double[])objectMapper.treeToValue(response, tArrayClass));
    }


}


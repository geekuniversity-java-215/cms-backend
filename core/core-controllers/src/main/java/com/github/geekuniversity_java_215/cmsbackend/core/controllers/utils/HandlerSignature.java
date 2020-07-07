package com.github.geekuniversity_java_215.cmsbackend.core.controllers.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

// Present a jrpc handler method with parameters(name, type) and return type
@Data
public class HandlerSignature {
    String name;
    Class<?> returnType;
    //Class<?> returnParameterizedType;
    Object returnValue;
    NavigableMap<String, HandlerParameter> params = new TreeMap<>();  // index by parameter name
    //List<HandlerParameter> paramsIndex = new ArrayList<>();           // index by parameter position

    @Data
    public static class HandlerParameter {
        String name;
        Class<?> type;
        Class<?> parameterizedType;
        int position;
        Object paramValue;

        public HandlerParameter(String name, Class<?> type, Class<?> parameterizedType, int position) {
            this.name = name;
            this.type = type;
            this.parameterizedType = parameterizedType;
            this.position = position;
        }
    }
}

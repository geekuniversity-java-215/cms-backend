package com.github.geekuniversity_java_215.cmsbackend.jrpc_controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.JrpcBase;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.JrpcException;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.response.JrpcErrorCode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.response.JrpcErrorResponse;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.response.JrpcResponse;
import org.springframework.http.HttpStatus;


// Создаватель HTTP ответов, инкапсулирует jrpc внутрь http
public class HttpResponseFactory {

    static public HttpResponse getOk(JrpcBase jrpcResponse) {
        return new HttpResponse(HttpStatus.OK, jrpcResponse);
    }

    static public HttpResponse getOk(JsonNode json) {
        return new HttpResponse(HttpStatus.OK, new JrpcResponse(json));
    }

    static public HttpResponse getUnauthorized() {

        JrpcErrorResponse error = new JrpcErrorResponse("Unauthorized", JrpcErrorCode.UNAUTHORIZED);
        return new HttpResponse(HttpStatus.UNAUTHORIZED, error);
    }

    static public HttpResponse getForbidden() {

        JrpcErrorResponse error = new JrpcErrorResponse("Unauthorized", JrpcErrorCode.FORBIDDEN);
        return new HttpResponse(HttpStatus.FORBIDDEN, error);
    }


    static public HttpResponse getError(JrpcException e) {

        // Include inner exception message
        String message = e.getMessage();
        if (e.getCause()!= null) {
            message = message + ": " + e.getCause().getMessage();
        }
        JrpcErrorResponse jrpcResult = new JrpcErrorResponse(message, e.getCode(), e.getData());
        return new HttpResponse(HttpStatus.BAD_REQUEST, jrpcResult);
    }


    static public HttpResponse getError(HttpStatus status, Throwable e) {

        String message = "Invalid request json: " + e.getMessage();
        JrpcErrorResponse jrpcResult = new JrpcErrorResponse(message, JrpcErrorCode.INVALID_REQUEST);
        return new HttpResponse(status, jrpcResult);
    }
}

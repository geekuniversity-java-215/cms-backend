package com.github.geekuniversity_java_215.cmsbackend.utils.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.protocol.http.HttpResponse;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.JrpcBase;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.JrpcException;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.response.JrpcErrorCode;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.response.JrpcErrorResponse;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.response.JrpcResponse;
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

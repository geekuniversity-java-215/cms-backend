package com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.response.JrpcErrorCode;


public class JrpcException extends RuntimeException {

    private JrpcErrorCode code; // код jrpc ошибки

    private JsonNode data;

    private JrpcException(){}

    public JrpcException(JrpcErrorCode code) {
        this.code = code;
    }

    public JrpcException(String s, JrpcErrorCode code) {
        super(s);
        this.code = code;
    }

    public JrpcException(String s, JrpcErrorCode code, Throwable throwable) {
        super(s, throwable);
        this.code = code;
    }

    public JrpcException(JrpcErrorCode code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }


    public JrpcErrorCode getCode() {
        return code;
    }

    public JsonNode getData() {
        return data;
    }

    @Override
    public String toString() {
        return "JrpcException{" +
            "code=" + code +
            ", data=" + data +
            '}';
    }
}

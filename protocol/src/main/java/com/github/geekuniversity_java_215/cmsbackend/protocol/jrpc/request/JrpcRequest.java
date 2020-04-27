package com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;


@JsonPropertyOrder({ "jsonrpc", "id", "method", "params"})
public class JrpcRequest extends JrpcRequestHeader {

    private JsonNode params;

    public JsonNode getParams() {
        return params;
    }

    public void setParams(JsonNode param) {
        this.params = param;
    }

    @Override
    public String toString() {
        return "JrpcRequest{" +
            "id=" + id +
            ", version='" + version + '\'' +
            ", method='" + method + '\'' +
            ", params=" + params +
            '}';
    }
}

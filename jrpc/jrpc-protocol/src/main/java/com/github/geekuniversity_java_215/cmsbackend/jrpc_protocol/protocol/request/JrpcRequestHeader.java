package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.JrpcBase;


/**
 * json-rps request without params
 * <br> Используется в ApiController, чтобы прочитать только заголовок jrpc запроса.
 * <br> Чтобы узнать имя метода, 
 * <br> (Не читая параметр запроса, т.к. десериализацией params занимается конкретный обработчик)
 */
@JsonIgnoreProperties(ignoreUnknown = true) // param не десериализуем
@JsonPropertyOrder({ "jsonrpc", "id", "method"})
public class JrpcRequestHeader extends JrpcBase {

    // Если не указана версия "jsonrpc", то считаем что "2.0" - багофича

    protected String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "JrpcRequestHeader{" +
            "id=" + id +
            ", version='" + version + '\'' +
            ", method='" + method + '\'' +
            '}';
    }
}

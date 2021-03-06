package com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.data;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.JrpcBase;
import org.springframework.http.HttpStatus;

public class HttpResponse {


    protected HttpStatus status;
    private JrpcBase result;

    //HttpResponse() {}
    public HttpResponse(HttpStatus status) {
        this.status = status;
    }

    public HttpResponse(HttpStatus status, JrpcBase result) {
        this.status = status;
        this.result = result;
    }

    

    public HttpStatus getStatus() {
        return status;
    }

    public JrpcBase getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
            "status=" + status +
            ", result=" + result +
            '}';
    }
}


/*




 */
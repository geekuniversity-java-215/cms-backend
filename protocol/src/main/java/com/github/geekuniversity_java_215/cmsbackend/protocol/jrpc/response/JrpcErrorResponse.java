package com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.JrpcBase;

/**
 * Represent json-rpc execution error response
 */
@JsonPropertyOrder({ "jsonrpc", "id", "error"})
public class JrpcErrorResponse extends JrpcBase {


    @JsonProperty("error")
    ErrorBody body = new ErrorBody();

    private JrpcErrorResponse(){}

    public JrpcErrorResponse(String message, JrpcErrorCode code) {
        this.body.message = message;
        this.body.code = code;
    }

    public JrpcErrorResponse(String message, JrpcErrorCode code, JsonNode data) {
        this.body.message = message;
        this.body.code = code;
        this.body.data = data;
    }

    public ErrorBody getBody() {
        return body;
    }

    private static class ErrorBody {
        // сообщение ошибки
        private String message;
        // код ошибки
        private JrpcErrorCode code;
        // additional error data
        private JsonNode data;


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public JrpcErrorCode getCode() {
            return code;
        }

        public void setCode(JrpcErrorCode code) {
            this.code = code;
        }

        public JsonNode getData() {
            return data;
        }

        public void setData(JsonNode data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "ErrorBody{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
        }
    }


    @Override
    public String toString() {
        return "JrpcErrorResponse{" +
            "id=" + id +
            ", version='" + version + '\'' +
            ", body=" + body +
            '}';
    }
}

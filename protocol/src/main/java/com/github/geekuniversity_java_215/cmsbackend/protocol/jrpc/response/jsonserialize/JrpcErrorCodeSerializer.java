package com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.response.jsonserialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.response.JrpcErrorCode;

import java.io.IOException;

// https://www.baeldung.com/jackson-custom-serialization
public class JrpcErrorCodeSerializer extends StdSerializer<JrpcErrorCode> {

    public JrpcErrorCodeSerializer() {
        this(null);
    }

    public JrpcErrorCodeSerializer(Class<JrpcErrorCode> t) {
        super(t);
    }

    @Override
    public void serialize(
            JrpcErrorCode value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeNumber(value.value());
    }
}
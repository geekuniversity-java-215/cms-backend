package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfirmRequest extends AbstractRequest {

    public ResponseEntity<Void> confirm(String confirmToken) {

        String url = String.format("http://%1$s:%2$s/registration/confirm?token=%3$s",
            this.clientProp.getAuthServer().getHostName(),
            this.clientProp.getAuthServer().getPort(),
            confirmToken);

        return performRequest(url, null, Void.class, null, HttpMethod.GET);
    }

}

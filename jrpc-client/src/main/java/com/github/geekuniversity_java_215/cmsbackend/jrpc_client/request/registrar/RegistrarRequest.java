package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractRequestWithOauth;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.user.UnconfirmedUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegistrarRequest extends AbstractRequest {

    public ResponseEntity<String> registrate(UnconfirmedUserDto newUser) {

        String url = String.format("http://%1$s:%2$s/registration/new",
            this.clientProp.getAuthServer().getHostName(),
            this.clientProp.getAuthServer().getPort());

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientProp.getLogin().getUsername(), clientProp.getLogin().getPassword());
        return performRequest(url, newUser, String.class, headers);

        //return performRequest(url, newUser, String.class);
    }

}

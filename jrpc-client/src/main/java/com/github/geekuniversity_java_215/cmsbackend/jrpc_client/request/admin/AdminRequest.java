package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.admin;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base.AbstractRequestWithOauth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdminRequest extends AbstractRequestWithOauth {

    public void revokeToken(String username) {

        String url = String.format("http://%1$s:%2$s/admin/user/revoke_token",
            this.clientProp.getAuthServer().getHostName(),
            this.clientProp.getAuthServer().getPort());

        ResponseEntity<Void> response = performRequest(url, username, Void.class);

        log.info("{}", response.getStatusCode());
    }

    public ResponseEntity<String> test() {

        String url = String.format("http://%1$s:%2$s/admin/test",
            this.clientProp.getAuthServer().getHostName(),
            this.clientProp.getAuthServer().getPort());

        return performRequest(url, null, String.class);
    }

}

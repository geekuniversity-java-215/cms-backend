package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.TokenDto;
import com.github.geekuniversity_java_215.cmsbackend.protocol.http.OauthResponse;
import com.github.geekuniversity_java_215.cmsbackend.protocol.token.GrantType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
public class OauthRequest extends AbstractRequest {

    public void authorize() {

        // Oauth2.0 authorization -------------------------------------------

        if (clientProp.getLogin().getRefreshToken().isRotten()) {
            log.debug("OAUTH GET TOKEN");
            obtainTokenInternal(GrantType.PASSWORD);
        }
        else if (clientProp.getLogin().getAccessToken().isRotten()) {
            log.info("OAUTH REFRESH TOKEN");
            obtainTokenInternal(GrantType.REFRESH);
        }
    }

    // ========================================================================

    private void obtainTokenInternal(GrantType grantType) {

        String getTokenURL = String.format("http://%1$s:%2$s/oauzz/token/%3$s",
            clientProp.getAuthServer().getHostName(),
            clientProp.getAuthServer().getPort(),
            grantType == GrantType.PASSWORD ? "get" : "refresh"
        );

        HttpHeaders headers = new HttpHeaders();
        if (grantType == GrantType.PASSWORD) {
            headers.setBasicAuth(clientProp.getLogin().getUsername(),
                clientProp.getLogin().getPassword());
        }
        else if (grantType == GrantType.REFRESH) {
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + clientProp.getLogin().getRefreshToken());
        }

        ResponseEntity<OauthResponse> response =
            performRequest(getTokenURL, Void.class, OauthResponse.class, headers);
        OauthResponse oauthResponse = response.getBody();

        if (oauthResponse != null) {

            clientProp.getLogin().setAccessToken(new TokenDto(oauthResponse.getAccessToken()));
            clientProp.getLogin().setRefreshToken(new TokenDto(oauthResponse.getRefreshToken()));

            log.debug("access_token: {}", clientProp.getLogin().getAccessToken());
            log.debug("access_token expiration: {}", clientProp.getLogin().getAccessToken().getExpiration());
            log.debug("refresh_token: {}", clientProp.getLogin().getRefreshToken());
            log.debug("refresh_token expiration: {}", clientProp.getLogin().getRefreshToken().getExpiration());
        }
    }
}




//    public BlackListResponse getBlackList(Long from) {
//
//        JrpcClientProperties.Login clientLogin = jrpcClientProperties.getLogin();
//
//        String checkTokenURL = String.format("http://%1$s:%2$s/oauzz/token/listblack",
//            jrpcClientProperties.getAuthServer().getHostName(),
//            jrpcClientProperties.getAuthServer().getPort());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(clientLogin.getUsername(), clientLogin.getPassword());
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        RequestEntity<String> requestEntity = RequestEntity
//            .post(URI.create(checkTokenURL))
//            .headers(headers)
//            .body("from=" + from);
//
//        ResponseEntity<BlackListResponse> response =
//            restTemplate.exchange(requestEntity, BlackListResponse.class);
//
//        return response.getBody();
//    }

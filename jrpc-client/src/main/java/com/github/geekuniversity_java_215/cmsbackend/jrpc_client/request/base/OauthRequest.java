package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.protocol.http.BlackListResponse;
import com.github.geekuniversity_java_215.cmsbackend.protocol.http.OauthResponse;
import com.github.geekuniversity_java_215.cmsbackend.protocol.token.GrantType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Scope("prototype")
@Lazy
@Slf4j
public class OauthRequest {

    private RestTemplate restTemplate;
    private JrpcClientProperties jrpcClientProperties;


    @Autowired
    public void setJrpcClientPropertiesDTO2(JrpcClientProperties jrpcClientProperties) {
        this.jrpcClientProperties = jrpcClientProperties;
    }


    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void refreshTokens() {
        log.info("OAUTH REFRESH TOKEN");

        obtainTokenAbstract(GrantType.REFRESH);
    }


    public BlackListResponse getBlackList(Long from) {

        JrpcClientProperties.Account clientAccount = jrpcClientProperties.getAccount();

        String checkTokenURL = String.format("http://%1$s:%2$s/oauzz/token/listblack",
            jrpcClientProperties.getAuthServer().getHostName(),
            jrpcClientProperties.getAuthServer().getPort());

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientAccount.getUsername(), clientAccount.getPassword());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RequestEntity<String> requestEntity = RequestEntity
            .post(URI.create(checkTokenURL))
            .headers(headers)
            .body("from=" + from);

        ResponseEntity<BlackListResponse> response =
            restTemplate.exchange(requestEntity, BlackListResponse.class);

        return response.getBody();
    }



    public void authorize() {
        JrpcClientProperties.Account clientAccount = jrpcClientProperties.getAccount();

        // Oauth2.0 authorization -------------------------------------------

        if (clientAccount.getRefreshToken().isRotten()) {
            obtainTokens();
        }
        else if (clientAccount.getAccessToken().isRotten()) {
            refreshTokens();
        }
    }

    // ========================================================================

    private void obtainTokenAbstract(GrantType grantType) {

        JrpcClientProperties.Account clientAccount = jrpcClientProperties.getAccount();

        //String params = String.format("grant_type=%1$s", grantType.getValue());

        String getTokenURL = String.format("http://%1$s:%2$s/oauzz/token/%3$s",
            jrpcClientProperties.getAuthServer().getHostName(),
            jrpcClientProperties.getAuthServer().getPort(),
            grantType == GrantType.PASSWORD ? "get" : "refresh"
        );


        RequestEntity<Void> requestEntity = null;

        if (grantType == GrantType.PASSWORD) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(clientAccount.getUsername(), clientAccount.getPassword());
            requestEntity = RequestEntity
                .post(URI.create(getTokenURL))
                .headers(headers)
                .build();

        }
        else if (grantType == GrantType.REFRESH) {

            String authorization = "Bearer " + clientAccount.getRefreshToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorization);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            requestEntity = RequestEntity
                .post(URI.create(getTokenURL))
                .headers(headers)
                .build();
        }

        Assert.notNull(requestEntity, "requestEntity is null");
        ResponseEntity<OauthResponse> response = restTemplate.exchange(requestEntity, OauthResponse.class);

        OauthResponse oauthResponse = response.getBody();

        if (oauthResponse != null) {

            clientAccount.setAccessToken(new TokenDto(oauthResponse.getAccessToken()));
            clientAccount.setRefreshToken(new TokenDto(oauthResponse.getRefreshToken()));

            log.debug("access_token: {}", clientAccount.getAccessToken());
            log.debug("access_token expiration: {}", clientAccount.getAccessToken().getExpiration());
            log.debug("refresh_token: {}", clientAccount.getRefreshToken());
            log.debug("refresh_token expiration: {}", clientAccount.getRefreshToken().getExpiration());
        }
    }

    public void obtainTokens() {
        log.debug("OAUTH GET TOKEN");
        obtainTokenAbstract(GrantType.PASSWORD);
    }

}

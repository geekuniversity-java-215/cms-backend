package com.github.geekuniversity_java_215.cmsbackend.oauth_protocol.protocol;

import lombok.Getter;

@Getter
public class OauthResponse {

    private String accessToken;
    private String refreshToken;

    protected OauthResponse() {}
    public OauthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "OauthResponse{" +
            "accessToken='" + accessToken + '\'' +
            ", refreshToken='" + refreshToken + '\'' +
            '}';
    }
}

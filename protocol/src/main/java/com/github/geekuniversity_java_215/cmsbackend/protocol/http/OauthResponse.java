package com.github.geekuniversity_java_215.cmsbackend.protocol.http;


public class OauthResponse {


    private String accessToken;
    private String refreshToken;

    protected OauthResponse() {}

    public OauthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }



    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return "OauthResponse{" +
            "accessToken='" + accessToken + '\'' +
            ", refreshToken='" + refreshToken + '\'' +
            '}';
    }
}

package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.AccessToken;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.RefreshToken;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.Token;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;


/**
 * Store current user Principals, AuthType and JWT(used for Bearer authentication, if have any)
 */
// https://www.baeldung.com/spring-bean-scopes
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScopeBean {

    @Getter
    private final HttpServletRequest request;

    private Token token;

    @Getter @Setter
    private AuthType authType = AuthType.BASIC_AUTH;

    @Autowired
    public RequestScopeBean(HttpServletRequest request) {
        this.request = request;
    }

//    public Token getToken() {
//        return token;
//    }

    public void setToken(Token token) {
        this.token = token;
    }

    public AccessToken getAccessToken() {
        return getToken(AccessToken.class);
    }

    public RefreshToken getRefreshToken() {
        return getToken(RefreshToken.class);
    }


    // ============================================================================

    // походу deprecated, после введения аннотации @ValidAuthenticationType
    // там проверяется по ValidAuthenticationType AuthType value
    private <T extends Token> T getToken(Class<T> tokenClass) {

        // check wrong token type (access instead refresh and vise versa)
        if (token.getClass() != tokenClass) {
            throw new AccessDeniedException("Unauthenticated");
        }
        return tokenClass.cast(token);
    }




}


//    private <T extends Token> T getToken(Class<T> tokenClass) {
//
//        Token token = requestScopeBean.getToken();
//        // check wrong token type (access instead refresh and vise versa)
//        if (token.getClass() != tokenClass) {
//            throw new AccessDeniedException("Unauthenticated");
//        }
//        return tokenClass.cast(token);
//    }

//    @PostConstruct
//    void postConstruct() {}



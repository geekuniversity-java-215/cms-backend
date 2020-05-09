package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.filter;

import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.AuthType;
import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.RequestScopeBean;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.JwtTokenService;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.TokenService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.AccessToken;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.RefreshToken;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.Token;
import com.github.geekuniversity_java_215.cmsbackend.protocol.token.TokenType;
import com.github.geekuniversity_java_215.cmsbackend.utils.SecurityUtils;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

//@Component("bearerRequestFilter")
@Component
@Slf4j
public class BearerRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;
    private final TokenService tokenService;
    private final RequestScopeBean requestScopeBean;

    @Autowired
    public BearerRequestFilter(@Qualifier("userDetailsCustomService")UserDetailsService userDetailsService,
                               JwtTokenService jwtTokenService, TokenService tokenService, RequestScopeBean requestScopeBean) {

        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.tokenService = tokenService;
        this.requestScopeBean = requestScopeBean;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        // get header "Authorization"
        final String requestTokenHeader = request.getHeader("Authorization");


        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (!StringUtils.isBlank(requestTokenHeader) &&
            requestTokenHeader.startsWith("Bearer ")) {
            try {
                String jwtToken = requestTokenHeader.substring(7);

                // decode & validate token, will throw exception if token not valid
                Claims claims = jwtTokenService.decodeJWT(jwtToken);

                // TOKEN is PRESENT in my DB (NOT deleted(by revoke/expiration)/blacklisted)
                Token token = getTokenByIdFromClaims(claims);

                UsernamePasswordAuthenticationToken authToken = getAuthToken(claims);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // save auth type
                if (token instanceof AccessToken) {
                    requestScopeBean.setAuthType(AuthType.ACCESS_TOKEN);
                }
                else if (token instanceof RefreshToken) {
                    requestScopeBean.setAuthType(AuthType.REFRESH_TOKEN);
                }

                // save token
                requestScopeBean.setToken(token);

                // configure Spring Security to manually set authentication

                // After setting the Authentication in the context, we specify
                // that the current user is authenticated.
                // So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception e) {
                log.info("JWT Token not valid: ", e);
                // User will stay Anonymous
            }

        }

        chain.doFilter(request, response);
    }

    // ------------------------------------------------------------


//    /**
//     * Check that JWT token not expired
//     */
//    private boolean tokenNotExpired(Claims claims) {
//        return claims.getExpiration().toInstant().toEpochMilli() > Instant.now().toEpochMilli();
//    }


    /**
     * Find Token in database by its id
     * @param claims
     * @return AccessToken or RefreshToken
     * @throws RuntimeException claims not valid, token not found
     */
    private Token getTokenByIdFromClaims(Claims claims) {

        Token result = null;

        // get token id
        long tokenId;
        try {
            tokenId = Long.parseLong(claims.getId());
        }
        catch (NumberFormatException e) {
            throw new BadCredentialsException("Token id not found");
        }
        if (tokenId <= 0) {
            throw new BadCredentialsException("Token id <= 0");
        }

        // get token by tokenId
        TokenType type = TokenType.getByName((String) claims.get("type"));
        if (type == null) {
            throw new BadCredentialsException("Token type unknown");
        }
        if (type == TokenType.ACCESS) {
            result = tokenService.findAccessToken(tokenId);
        } else if (type == TokenType.REFRESH) {
            result = tokenService.findRefreshToken(tokenId);
        }
        if (result == null) {
            throw new AuthenticationCredentialsNotFoundException("Token not found");
        }

        return result;
    }


    /**
     * Cook spring security.authentication token
     * <br> Load User details from DB by token claims
     * @param claims token claims
     * @throws RuntimeException user not found, token type not allowed
     */
    private UsernamePasswordAuthenticationToken getAuthToken(Claims claims) {

        UsernamePasswordAuthenticationToken result = null;

        // load user from DB
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Готовим user grantedAuthorities
        TokenType type = TokenType.getByName((String)claims.get("type"));
        Collection<? extends GrantedAuthority> grantedAuthorities;

        // Для access_token возвращаем права(роли) пользователя из БД, какие ему назначены
        if (type == TokenType.ACCESS) {
            grantedAuthorities = userDetails.getAuthorities();
        }
        // Для refresh_token возвращаем только роль REFRESH,никакими другими ресурсами(контроллерами) кроме обновления
        // с этим токеном пользоваться нельзя
        else if (type == TokenType.REFRESH) {
            grantedAuthorities = SecurityUtils.rolesToGrantedAuthority(Collections.singletonList(UserRole.REFRESH));
        }
        else {
            throw new BadCredentialsException("Token type " + type + " not allowed");
        }

        result = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);

        return result;
    }


}



//@Component
//RegistrationBean
//@WebFilter(urlPatterns = "/hellow")


// КАК СОХРАНЯТЬ ДАННЫЕ В СЕССИЮ ------------------------------------------------------------------------------

//        Сохраняем claims в сессии
//    request.getSession().setAttribute("claims", claims);


//        Читаем где-то дальше в другом месте
//    ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
//    HttpSession session = attr.getRequest().getSession();  // getSession(true) == create new if not exists
//    Claims claims = (Claims)session.getAttribute("claims");
// -------------------------------------------------------------------------------------------------------------


// КАК ПОЛУЧИТЬ ТЕКУЩИЙ HttpServletRequest ---------------------------------------------------------------------

//    HttpServletRequest curRequest =
//            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                    .getRequest();
// -------------------------------------------------------------------------------------------------------------



//    private Token createToken(Claims claims) {
//
//
//
//        if (type==TokenType.ACCESS) {
//
//
//
//
//        }
//        else if (type==TokenType.REFRESH) {
//
//
//
//        }
//    }




//    @Override
//    public void doFilter(ServletRequest requestBase, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest)requestBase;


//    private Token findToken(Claims claims) {
//
//        Token result = null;
//
//        TokenType type = TokenType.parseName((String)claims.get("type"));
//        Long tokenId = Long.valueOf(claims.getId());
//
//        if (type == TokenType.ACCESS) {
//            result = tokenService.findAccessToken(tokenId);
//        }
//        else if(type == TokenType.REFRESH) {
//            result = tokenService.findRefreshToken(tokenId);
//        }
//
//        return result;
//    }



//    /**
//     * Token is known to server(not deleted/blacklisted)
//     */
//    private boolean tokenIsPresent(Claims claims) {
//
//        boolean result = false;
//
//        TokenType type = TokenType.parseName((String) claims.get("type"));
//
//        Long tokenId = Long.valueOf(claims.getId());
//
//        if (type == TokenType.ACCESS) {
//            result = tokenService.findAccessToken(tokenId) != null;
//        } else if (type == TokenType.REFRESH) {
//            result = tokenService.findRefreshToken(tokenId) != null;
//        }
//
//        return result;
//    }

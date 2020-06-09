package com.github.geekuniversity_java_215.cmsbackend.core.configurations.filters;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.oauth_utils.data.TokenType;
import com.github.geekuniversity_java_215.cmsbackend.oauth_utils.services.JwtTokenService;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

@Component
@Slf4j
public class BearerRequestResourceFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    @Autowired
    public BearerRequestResourceFilter(JwtTokenService jwtTokenService, UserService userService) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
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

                UsernamePasswordAuthenticationToken authToken = getAuthToken(claims);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

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


    /**
     * Cook spring security.authentication token
     * <br> Load User from DB by token claims.subject
     * @param claims token claims
     * @throws RuntimeException user not found, user without roles, token type not allowed
     */
    private UsernamePasswordAuthenticationToken getAuthToken(Claims claims) {

        UsernamePasswordAuthenticationToken result;

        TokenType type;
        try {
            type = TokenType.getByName((String)claims.get("type"));
        }
        catch (Exception e) {
            throw new BadCredentialsException("Unknown token type");
        }
        if (type != TokenType.ACCESS) {
            throw new BadCredentialsException("Token type " + type + " not allowed");
        }

        // load user from DB
        User user = userService.findByUsername(claims.getSubject())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(user.getRoles().size() == 0) {
            throw new InsufficientAuthenticationException("User without roles");
        }

        // Готовим grantedAuthorities
        // Берем роли пользователя из БД, какие ему назначены
        Collection<? extends GrantedAuthority> grantedAuthorities = UserRole.rolesToGrantedAuthority(user.getRoles());

        // Готовим UserDetails
        UserDetails userDetails =
            new org.springframework.security.core.userdetails.User(
                claims.getSubject(),
                "",
                grantedAuthorities);

        result = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);

        return result;
    }

}
package com.github.geekuniversity_java_215.cmsbackend.authserver.config.filter;

import com.github.geekuniversity_java_215.cmsbackend.authserver.config.AuthType;
import com.github.geekuniversity_java_215.cmsbackend.authserver.config.RequestScopeBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component("basicAuthRequestCustomFilter")
@Component
@Slf4j
public class BasicAuthRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final RequestScopeBean requestScopeBean;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BasicAuthRequestFilter(@Qualifier("userDetailsCustomService")UserDetailsService userDetailsService,
                                  RequestScopeBean requestScopeBean, PasswordEncoder passwordEncoder) {

        this.userDetailsService = userDetailsService;
        this.requestScopeBean = requestScopeBean;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Implement BasicAuth manually
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        // get header "Authorization"
        final String requestBasicHeader = request.getHeader("Authorization");

        // Parsing BasicAuth string
        if (requestBasicHeader != null &&
            requestBasicHeader.startsWith("Basic ")) {

            try {

                String basicAuth = requestBasicHeader.substring(6);

                byte[] decodedBytes = Base64.decodeBase64(basicAuth);
                String decodedAuth = new String(decodedBytes);
                
                String[] slitted = decodedAuth.split(":", 2);
                String userName = slitted[0];
                String password = slitted[1];

                UsernamePasswordAuthenticationToken authToken = getAuthToken(userName, password);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                requestScopeBean.setAuthType(AuthType.BASIC_AUTH);

                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception e) {
                log.info("BasicAuth string not valid", e);
            }
        }

        chain.doFilter(request, response);
    }


    /**
     * Load User details from DB, compare password hashes
     * @param userName login
     * @param password password
     */
    private UsernamePasswordAuthenticationToken getAuthToken(String userName, String password) {

        UsernamePasswordAuthenticationToken result = null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        if (userDetails != null &&
            passwordEncoder.matches(password, userDetails.getPassword())) {

            result = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        return result;
    }

}
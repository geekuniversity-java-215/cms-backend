package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import com.github.geekuniversity_java_215.cmsbackend.authserver.repository.token.AccessTokenRepository;
import com.github.geekuniversity_java_215.cmsbackend.authserver.repository.token.RefreshTokenRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.AccessToken;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.RefreshToken;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.Token;
import com.github.geekuniversity_java_215.cmsbackend.core.services.PersonService;
import com.github.geekuniversity_java_215.cmsbackend.protocol.http.OauthResponse;
import com.github.geekuniversity_java_215.cmsbackend.protocol.token.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.geekuniversity_java_215.cmsbackend.authserver.config.SpringConfiguration.ISSUER;

@Service
@Transactional
public class TokenService {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final JwtTokenService jwtTokenService;
    private final PersonService personService;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistTokenService blacklistTokenService;

    @Autowired
    public TokenService(JwtTokenService jwtTokenService,
                        PersonService personService,
                        AccessTokenRepository accessTokenRepository,
                        RefreshTokenRepository refreshTokenRepository,
                        BlacklistTokenService blacklistTokenService) {

        this.jwtTokenService = jwtTokenService;
        this.personService = personService;
        this.accessTokenRepository = accessTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.blacklistTokenService = blacklistTokenService;
    }


    /**
     * Create new token, delete previous(on refreshing)
     *
     * @param login
     * @param refreshToken current refresh_token if available
     * @return
     */
    public OauthResponse issueTokens(String login, RefreshToken refreshToken) {

        //boolean doRefresh = refreshToken != null;

        RefreshToken oldRefreshToken = refreshToken;
        AccessToken accessToken = null;
        //
        String accessTokenString = null;
        String refreshTokenString = null;
        //
        //
        // find person
        Person person = personService.findByLogin(login);
        if(person == null) {
            throw new UsernameNotFoundException("Person not exists: " + login);
        }


        // 1. Refresh Token -------------------------------------------------------------------

        long ttl = TokenType.REFRESH.getTtl();
        Instant expiredAt = Instant.now().plusSeconds(ttl);

        refreshToken = new RefreshToken(person, expiredAt);
        refreshTokenRepository.save(refreshToken);

        Set<String> refreshRoles = new HashSet<>(Collections.singletonList(UserRole.REFRESH));

        refreshTokenString = jwtTokenService.createJWT(
                TokenType.REFRESH, refreshToken.getId(), ISSUER, person.getLogin(), refreshRoles, ttl);

        // 2. Access Token ---------------------------------------------------------------------

        expiredAt = Instant.now().plusSeconds(TokenType.ACCESS.getTtl());
        accessToken = new AccessToken(person, refreshToken, expiredAt);
        refreshToken.setAccessToken(accessToken);

        accessTokenRepository.save(accessToken);

        Set<String> roles =
                person.getRoles().stream().map(UserRole::getName).collect(Collectors.toSet());

        accessTokenString = jwtTokenService.createJWT(
                TokenType.ACCESS, accessToken.getId(), ISSUER, person.getLogin(), roles, TokenType.ACCESS.getTtl());



        // Delete here deprecated access and refresh token --------------------------------------

        if (oldRefreshToken != null) {
            AccessToken oldAccessToken = oldRefreshToken.getAccessToken();
            if (oldAccessToken != null) {
                blacklistTokenService.blacklist(oldAccessToken);
            }
            // will delete both token(old refresh and old access if has)
            delete(oldRefreshToken);
        }
        return new OauthResponse(accessTokenString, refreshTokenString);
    }





    public AccessToken findAccessToken(Long id) {

        AccessToken result = null;

        if (id != null) {
            result = accessTokenRepository.findById(id).orElse(null);
        }
        return result;
    }


    public RefreshToken findRefreshToken(Long id) {

        RefreshToken result = null;

        if (id != null) {
            result = refreshTokenRepository.findById(id).orElse(null);
        }
        return result;
    }

    public void delete(Token token) {

        try {
            if (token instanceof AccessToken) {
                accessTokenRepository.delete((AccessToken) token);
            } else if (token instanceof RefreshToken) {
                // will also delete access_token
                refreshTokenRepository.delete((RefreshToken) token);
            }
        } catch (Exception e) {
            log.error("delete token error: " + token.toString(), e);
        }
    }

    public void deleteByPerson(Person person) {
        refreshTokenRepository.deleteByPerson(person);
    }


    public void vacuum() {

        accessTokenRepository.vacuum();
        refreshTokenRepository.vacuum();

    }
}



//        Long accessS = Instant.now().getEpochSecond() - TokenType.ACCESS.getTtl();
//        Instant access = Instant.ofEpochSecond(accessS);
//
//        Long refreshS = Instant.now().getEpochSecond() - TokenType.REFRESH.getTtl();
//        Instant refresh = Instant.ofEpochSecond(refreshS);
//
//        tokenRepository.vacuum(access, refresh);






//    /**
//     * Return token that client used in bearer authentication (if has)
//     */
//    public Token getTokenUsedInAuthentication() {
//        return findById(getAuthTokenId());
//    }



//    /**
//     * Verify token type (if exists)
//     * <br> Token retrieved from session (originated from authentication)
//     * @param tokenType TokenType
//     * @param role role, issued to token
//     * @Throw AccessDeniedException if conditions failed. do nothing if token not exists.
//     */
//    public Long checkTokenAuthorization(TokenType tokenType, Set<String> roles) {
//
//        Long result = null;
//
//        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
//        HttpSession session = attr.getRequest().getSession();
//
//        Claims claims = (Claims)session.getAttribute("claims");
//
//        // user has authorised by TOKEN - check it
//        if (claims != null) {
//
//            result = Long.valueOf(claims.getId());
//            Set<String> authorities = rolesToSet(claims.get("authorities"));
//
//            // token type and role
//            if (!claims.get("type").equals(tokenType.getName()) ||
//                !authorities.containsAll(roles)) {
//
//                throw new AccessDeniedException("Token unauthorized");
//            }
//
//        }
//
//        return result;
//    }







////user = new User(userDetails.getUsername(), "[PROTECTED]", authorities);
//        cUser = userService.findByName(userDetails.getUsername()).orElseThrow(
//                () -> new RuntimeException("User not exists: " + userDetails.getUsername()));
//
//
//
//
//
////cuser.setRolesEx(userDetails.getAuthorities());
//
////authorities = new ArrayList<>(userDetails.getAuthorities());
////user = new User(userDetails.getUsername(), "[PROTECTED]", authorities);

//authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "REFRESH"));


//    public boolean isTokenEnabled(Long id) {
//
//        AtomicBoolean result = new AtomicBoolean(false);
//        if (id != null) {
//            tokenRepository.findById(id).ifPresent(token -> result.set(token.isEnabled()));
//        }
//        return result.get();
//    }




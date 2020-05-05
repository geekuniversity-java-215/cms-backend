package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.base;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

public class TokenDto {

    public static final TokenDto EMPTY = new TokenDto();

    private final Claims claims;
    private final String tokenString;

    private TokenDto() {
        tokenString = null;
        claims = new DefaultClaims();
        // новый токен уже заранее протухший
        claims.setExpiration(Date.from(Instant.EPOCH));
    }

    public TokenDto(String tokenString) {

        this.tokenString = tokenString;
        claims = getClaimsInternal(tokenString);
    }

    public Claims getClaims() {
        return claims;
    }

    public String getTokenString() {
        return tokenString;
    }


    public boolean isRotten() {

        boolean result = true;
        if (claims != null) {
            result = Instant.now().toEpochMilli() > claims.getExpiration().toInstant().toEpochMilli();
        }
        return result;
    }

    public Long getId() {
        Long result = null;

        if (claims != null) {
            result = Long.parseLong(claims.getId());
        }
        return result;
    }

    public Instant getExpiration() {

        Instant result = null;

        if (claims != null) {

            result = claims.getExpiration().toInstant();
        }
        return result;
    }


    // get claims without checking key signing
    private Claims getClaimsInternal(String token) {

        final AtomicReference<Claims> result = new AtomicReference<>();

        SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
            @Override
            public Key resolveSigningKey(JwsHeader header, Claims claims) {
                result.set(claims);
                // Examine header and claims
                return null; // will throw exception, can be caught in caller
            }
        };

        try {

            Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            // no signing key on client.
            // We trust that this JWT came from the trusted server and has been verified(issued) there.
        }
        return result.get();
    }


    @Override
    public String toString() {
        return tokenString;
    }
}

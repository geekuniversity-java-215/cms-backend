package com.github.geekuniversity_java_215.cmsbackend.oauth_utils.services;

import com.github.geekuniversity_java_215.cmsbackend.oauth_utils.data.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class JwtTokenService implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    // "jwt.secret" property should be declared in module/app properties that use this module
    @Value("${jwt.secret:empty}")
    private String SECRET_KEY;

    public String createJWT(TokenType tokenType,
                            String id,
                            String issuer,
                            String subject,
                            Set<String> roles) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Instant now = Instant.now();

        long epoch = now.getEpochSecond(); //System.currentTimeMillis();
        Date nowDate = Date.from(now);

        //We will sign our JWT with our ApiKey secret

        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("authorities", roles);
        customClaims.put("type", tokenType.getName());
        //customClaims.put("approved", approved);

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
            .setClaims(customClaims)
            .setId(id)
            .setIssuedAt(nowDate)
            .setSubject(subject)
            .setIssuer(issuer)
            .signWith(signatureAlgorithm, DatatypeConverter.parseBase64Binary(SECRET_KEY));


        long expired = epoch + tokenType.getTtl();
        Date exp = new Date(expired * 1000);
        builder.setExpiration(exp);


        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }


    /**
     * Decode and validate token by SECRET_KEY
     * @param jwt token
     * @return Claims
     */
    public Claims decodeJWT(String jwt) {

        return Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
            .parseClaimsJws(jwt).getBody();
    }


//    public String createJWT(TokenType tokenType,
//                            Long id,
//                            String issuer,
//                            String subject,
//                            Set<String> roles,
//                            long ttl) {
//
//        return createJWT(tokenType, id.toString(), issuer, subject, roles, ttl);
//    }


    /*



	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify token, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("authorities", userDetails.getAuthorities());
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	*/



}

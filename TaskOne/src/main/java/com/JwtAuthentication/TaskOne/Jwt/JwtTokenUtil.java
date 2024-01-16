package com.JwtAuthentication.TaskOne.Jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {
    
    private final long TOKEN_EXPIRATION_DATE = 5 * 60 * 60;
    
    @Value("${jwt.secretKey}") // SHA512 encoded secretKey
    private String secretKey;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerate(claims, userDetails.getUsername());
    }

    private String doGenerate(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims().add(claims).and()
                .claims().subject(username).and()
                .claims().issuedAt(new Date()).and()
                .claims().expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_DATE * 1000)).and()
                .signWith(key())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token){
        return getTokenClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        final Date isExpired = getTokenClaim(token, Claims::getExpiration);
        return isExpired.before(new Date());
    }

    private <T> T getTokenClaim(String token, Function<Claims, T> claimResolver){
        final Claims claim = getAllTokenClaim(token);
        return claimResolver.apply(claim);
    }

    private Claims getAllTokenClaim(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //used for securely generating SecretKeys
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}

package com.nani.spring.security.usersservice;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "this_is_a_very_long_secret_key_for_hs256_1234";
	
    public String generateToken(String username , String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("Role", role);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSigningKey())
                .compact();
    }
    
    private Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public Claims verifySigAndExtractClaims(String token) {
        try {
        	return Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())   // use verifyWith instead of setSigningKey
                    .build()
                    .parseSignedClaims(token)      // new method
                    .getPayload(); 
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
    
    public String extractUserName(String token) {
    	return verifySigAndExtractClaims(token).getSubject();
    }
    
    public Date getExpiry(String token) {
    	return verifySigAndExtractClaims(token).getExpiration();
    }
    
    public boolean isTokenExpired(String token) {
    	return getExpiry(token).before(new Date());
    }

}

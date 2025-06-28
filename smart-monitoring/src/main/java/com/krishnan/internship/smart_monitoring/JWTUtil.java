package com.krishnan.internship.smart_monitoring;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.expiry-date}")
    private long EXPIRY_DATE ;

    // Generate Tokens
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("SmartMonitoringApplication")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_DATE))
                .signWith(getKeys(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getKeys() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Validate Tokens
    public boolean validateTokens(String username, String token) {
        try {
            String extractedUser = extractUser(token);
            return username.equals(extractedUser) && !extractExpiration(token).before(new Date());
        } catch (JwtException e) {
            System.out.println("Invalid token detected: " + e.getMessage());
            return false;
        }
    }

    // Valid token
    public boolean isValidToken(String token){
        return !extractExpiration(token).before(new Date()) ;
    }

    // Extract Username
    public String extractUser(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKeys())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract Expiration
    public Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKeys())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}

package com.krishnan.internship.smart_monitoring;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {
    private final String SECRET_KEY = "xPzR4d5J7jM2h5vO9rXoYw8y3kAaM2i6vE9hPfKqW2U5pZ9wLkB9fT9hFh4Wg7U" ;
    private final long EXPIRY_DATE = 24 * 60 * 60 * 100 ;

    // Generate Tokens
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("SmartMonitoringApplication")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_DATE))
                .signWith(getKeys() , SignatureAlgorithm.ES256)
                .compact() ;
    }
    public Key getKeys(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()) ;
    }

    // Validate Tokens
    public boolean validateTokens(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getKeys())
                    .build()
                    .parseClaimsJws(token) ;
            return true ; 
        }
        catch(JwtException e){
            System.out.println("JWT Token is invalid and you should not be moving ahead !!") ; 
            e.printStackTrace() ; 
            return false ; 
        }
    }
    
    // Extract user :
    public String extractUser(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKeys())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject() ;
    }
}

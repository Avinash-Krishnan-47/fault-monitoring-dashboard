package com.krishnan.internship.smart_monitoring;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie [] getAuth = request.getCookies() ;

        if(getAuth != null && getAuth.length != 0){
            Cookie auth = getAuth[0] ;
            String token = auth.getValue() ;
            String username = jwtUtil.extractUser(token) ;
            if(username != null && jwtUtil.validateTokens(username, token)){
                UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username , null , Collections.emptyList()) ;
                SecurityContextHolder.getContext().setAuthentication(upToken) ;
            }
        }


            // Token Validation (Optional: Implement proper validation logic in jwtUtil)
//            if (username != null && jwtUtil.validateTokens(username , token)) {
//                UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(
//                        username,
//                        null,
//                        Collections.emptyList()
//                );
//
//                SecurityContextHolder.getContext().setAuthentication(upToken) ;

        filterChain.doFilter(request, response);
    }
}

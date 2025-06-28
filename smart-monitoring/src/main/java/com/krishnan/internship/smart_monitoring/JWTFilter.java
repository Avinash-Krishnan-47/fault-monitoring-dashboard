package com.krishnan.internship.smart_monitoring;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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

        String getAuth = request.getHeader("Authorization");

        if (getAuth != null && getAuth.startsWith("Bearer ")) {
            String token = getAuth.substring(7);

            String username = jwtUtil.extractUser(token);

            // Token Validation (Optional: Implement proper validation logic in jwtUtil)
            if (username != null && jwtUtil.validateTokens(username , token)) {
                UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.emptyList()
                );

                SecurityContextHolder.getContext().setAuthentication(upToken) ;
            }
        }

        filterChain.doFilter(request, response);
    }
}

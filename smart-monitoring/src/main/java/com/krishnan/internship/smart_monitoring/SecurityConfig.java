package com.krishnan.internship.smart_monitoring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for development/testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Permit all endpoints
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable()) // Correct lambda style for disabling form login
                .httpBasic(basic -> basic.disable()); // Correct lambda style for disabling basic auth

        return http.build();
    }
}

package com.krishnan.internship.smart_monitoring;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private RedisService redisService ;

    @DeleteMapping("/account")
    public void deleteToken(@CookieValue(value = "auth_token") String token , HttpServletResponse response){
        if(token != null){
            redisService.inValidateToken(token) ;
            System.out.println("Redis Service for logout class implemented ....") ;
        }
        ResponseCookie cookie = ResponseCookie.from("auth_token" , "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build() ;

        System.out.println("Logout Successful") ;
        response.addHeader("Set-Cookie" , cookie.toString()) ;
        return ;
    }
}

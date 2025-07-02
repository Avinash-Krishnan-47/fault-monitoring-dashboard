package com.krishnan.internship.smart_monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/logout")
public class LogoutClass {
    @Autowired
    private RedisService redisService ;

    @DeleteMapping("/account")
    public void deleteToken(@RequestHeader("Authorization") String token){
        redisService.inValidateToken(token.substring(7)) ;
        return ;
    }
}

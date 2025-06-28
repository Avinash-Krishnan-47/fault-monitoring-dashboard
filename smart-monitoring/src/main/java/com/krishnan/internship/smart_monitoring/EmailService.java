package com.krishnan.internship.smart_monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    public JavaMailSender javaMailSender ;

    @Autowired
    private RedisService redisService ;

    public void setJavaMailSender(String email){
        String token = generateCode() ;
        SimpleMailMessage sm = new SimpleMailMessage() ;
        sm.setSubject("Your Password reset code") ;
        sm.setTo(email) ;
        sm.setText("Your code for requested Password change is : " + token + "\n" + "Use this code to reset your password") ;

        redisService.storeCode(email , token , 7) ;
        javaMailSender.send(sm) ;

        System.out.println("Mail sent successfully !!") ;
    }

    public String generateCode(){
        String key = UUID.randomUUID().toString().replace("-" , "").substring(0,8) ;
        return key ;
    }
}

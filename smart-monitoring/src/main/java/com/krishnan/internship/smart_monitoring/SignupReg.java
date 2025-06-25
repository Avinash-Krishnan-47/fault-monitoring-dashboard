package com.krishnan.internship.smart_monitoring;

import org.springframework.stereotype.Component;

@Component
public class SignupReg {

    String username ;
    String email ;
    String password ;

    public void setUsername(String username){
        this.username = username ;
    }
    public void setPassword(String password){
        this.password = password ;
    }
    public void setEmail(String email){
        this.email = email ;
    }

}

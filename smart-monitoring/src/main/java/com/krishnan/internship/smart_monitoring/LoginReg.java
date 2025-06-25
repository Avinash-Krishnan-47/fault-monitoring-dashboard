package com.krishnan.internship.smart_monitoring;

import org.springframework.stereotype.Component;

@Component
public class LoginReg {

    String username ;
    String password ;

    public void setUsername(String username){
        this.username = username ;
    }
    public void setPassword(String password){
        this.password = password ;
    }
}

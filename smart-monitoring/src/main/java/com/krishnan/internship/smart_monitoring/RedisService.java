package com.krishnan.internship.smart_monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    public void storeCode(String email , String code , long timeDuration){
        stringRedisTemplate.opsForValue().set(email , code , timeDuration , TimeUnit.MINUTES) ;
    }

    public String getCode(String email){
        return stringRedisTemplate.opsForValue().get(email) ;
    }

    public void deleteCode(String email){
        stringRedisTemplate.delete(email) ;
    }
}

package com.gangE.DongoShop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisExample {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisExample(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String readFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
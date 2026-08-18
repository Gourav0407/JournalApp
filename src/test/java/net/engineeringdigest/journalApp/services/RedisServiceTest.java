package net.engineeringdigest.journalApp.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Disabled
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redis(){
        redisTemplate.opsForValue().set("email","example@abc.com");
        String name= (String)redisTemplate.opsForValue().get("name");
    }
}

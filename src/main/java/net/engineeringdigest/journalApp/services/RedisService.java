package net.engineeringdigest.journalApp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    private final RedisTemplate redisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public RedisService(RedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    public <T> T getValue(String key, Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            return mapper.readValue(o.toString(),entityClass);
        }catch (JsonProcessingException e){
            log.error("Exeption",e);
            return null;
        }

    }

    public void setValue(String key, Object o, Long ttl){
        try{
            String json= mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,json,ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("Exception",e);
        }

    }
}

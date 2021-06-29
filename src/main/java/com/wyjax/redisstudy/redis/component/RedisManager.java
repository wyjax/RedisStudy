package com.wyjax.redisstudy.redis.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class RedisManager {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;


    public RedisManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void set(String key, Object value) {
        if (!StringUtils.hasText(key) || Optional.ofNullable(value).isEmpty()) {
            throw new IllegalStateException("진행불가");
        }

        String stringValue = convertObjectToString(value);

        redisTemplate.opsForValue().set(key, stringValue);
    }

    public <T> T get(String key, Class<T> classType) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalStateException("key 값이 비어있습니다.");
        }
        if (!hasKey(key)) {
            throw new IllegalStateException("value 값이 없습니다.");
        }

        Object value = redisTemplate.opsForValue().get(key);

        return castObject(value, classType);
    }

    private String convertObjectToString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new IllegalStateException("Object to String 변환 실패");
        }
    }

    private <T> T castObject(Object value, Class<T> classType) {
        try {
            T t = objectMapper.readValue(value.toString(), classType);

            return t;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("String to Object 변환 실패");
        }
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}

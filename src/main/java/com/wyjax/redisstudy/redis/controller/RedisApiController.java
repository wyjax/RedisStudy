package com.wyjax.redisstudy.redis.controller;

import com.wyjax.redisstudy.redis.component.RedisManager;
import com.wyjax.redisstudy.redis.domain.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisApiController {
    private final RedisManager redisManager;


    @GetMapping("/redis/test")
    public ResponseEntity getRedis() {
        Login login = redisManager.get("login", Login.class);

        return ResponseEntity.ok(login);
    }

    @PostMapping("/redis/test")
    public ResponseEntity saveRedis() {
        Login login = new Login();
        redisManager.set("login", login);

        return ResponseEntity.ok().build();
    }
}

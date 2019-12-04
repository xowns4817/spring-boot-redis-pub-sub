package com.example.redis.pubsub.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RedisPubsubApplication {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisPubsubApplication.class, args);
    }

    @GetMapping("/test")
    public void Test() {
        redisTemplate.convertAndSend("taejun9418", "I'm Kim Tae Jun.!");
    }
}

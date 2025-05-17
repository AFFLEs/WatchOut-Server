package com.affles.watchout.server.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    public void saveRefreshToken(String userId, String refreshToken, long seconds) {
        redisTemplate.opsForValue().set("RT:" + userId, refreshToken, Duration.ofSeconds(seconds));
    }

    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get("RT:" + userId);
    }

    public void deleteRefreshToken(String userId) {
        redisTemplate.delete("RT:" + userId);
    }

    public void addTokenToBlacklist(String accessToken, long expirationMillis) {
        redisTemplate.opsForValue().set("BL:" + accessToken, "logout", Duration.ofMillis(expirationMillis));
    }

    public boolean isTokenBlacklisted(String accessToken) {
        return redisTemplate.hasKey("BL:" + accessToken);
    }
}
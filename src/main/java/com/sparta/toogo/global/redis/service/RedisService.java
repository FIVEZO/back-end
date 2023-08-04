package com.sparta.toogo.global.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // 발급된 Access 꺼내기
    public String getAccessToken(String refreshToken) {
        return redisTemplate.opsForValue().get(refreshToken);
    }

    // 발급된 Access Token과 Refresh Token을 저장 (key : refresh, value : access)
    public void saveAccessToken(String refreshToken, String accessToken, Date refreshExpire) {
        redisTemplate.opsForValue().set(refreshToken, accessToken); // key : value
        redisTemplate.expireAt(refreshToken, refreshExpire); // 키 값에 해당 객체 만료일(자동삭제) 설정
    }

    // token 삭제
    public void deleteToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

    // key를 통해 value 리턴
    public String getCode(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 유효 시간 동안(key, value)저장
    public void setCodeExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public String findKeyByValue(String value) {
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            String val = redisTemplate.opsForValue().get(key);
            if (value.equals(val)) {
                return key;
            }
        }
        return null;
    }

    public void deleteCode(String key) {
        redisTemplate.delete(key);
    }
}
package com.sparta.toogo.global.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;

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
}
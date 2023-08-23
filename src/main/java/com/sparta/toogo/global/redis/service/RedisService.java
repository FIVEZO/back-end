package com.sparta.toogo.global.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jasypt.key}")
    private String tokenKey;

    private final RedisTemplate<String, String> redisTemplate;

    // 발급된 Access 꺼내기
    public String getRefreshToken(String accessToken) {
        String encodingRefreshToken = redisTemplate.opsForValue().get(accessToken);
        return jasyptDecoding(encodingRefreshToken);
    }

    // 발급된 Access Token과 Refresh Token을 저장 (key : access, value : refresh)
    public void saveAccessToken(String accessToken, String subRefreshToken, Date refreshExpire) {
        String encodingRefreshToken = jasyptEncoding(subRefreshToken);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Long refreshExpireToLong = refreshExpire.getTime() / 1000L;
        Duration expireDuration = Duration.ofSeconds(refreshExpireToLong);
        valueOperations.set(accessToken, encodingRefreshToken, expireDuration);
    }

    // token 삭제
    public void deleteToken(String accessToken) {
        log.info(accessToken);
        redisTemplate.delete(accessToken);
    }

    // key를 통해 value 리턴
    public String getCode(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 유효 시간 동안(key, value)저장
    public void setCodeExpire(String key, String value, Long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public String findKeyByValue(String value) {
        Set<String> keys = redisTemplate.keys("*@*");
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

    public String jasyptEncoding(String val) {
        String key = tokenKey;
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword(key);
        pbeEnc.setIvGenerator(new RandomIvGenerator());
        return pbeEnc.encrypt(val);
    }

    public String jasyptDecoding(String encryptedVal) {
        String key = tokenKey;
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword(key);
        pbeEnc.setIvGenerator(new RandomIvGenerator());
        return pbeEnc.decrypt(encryptedVal);
    }
}
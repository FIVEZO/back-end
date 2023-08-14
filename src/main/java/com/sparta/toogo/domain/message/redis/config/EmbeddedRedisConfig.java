//package com.sparta.toogo.domain.message.redis.config;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import redis.embedded.RedisServer;
//
//@Profile("local")       // local 환경에서만 실행되도록 설정.  로컬 환경일 경우, 채팅 서버가 실행될 때 내장형 레디스(Embedded Redis)도 동시에 실행된다
//@Configuration
//public class EmbeddedRedisConfig {
//    @Value("${spring.data.redis.host}")
//    private int redisHost;
//
////    @Value("${spring.redis.port}")
//    @Value("${spring.data.redis.port}")
//    private int redisPort;
//
//    private RedisServer redisServer;        // redis 데이터베이스 서버
//
//    // redis 서버 초기화 및 시작
//    @PostConstruct
//    public void redisServer() {
//        redisServer = new RedisServer(redisPort);
//        redisServer.start();
//    }
//
//    // redis 서버 중지. container 에서 빈 제거 전 실행
//    @PreDestroy
//    public void stopRedis() {
//        if (redisServer != null) {
//            redisServer.stop();
//        }
//    }
//}
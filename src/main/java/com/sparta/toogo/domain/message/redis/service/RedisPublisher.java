package com.sparta.toogo.domain.message.redis.service;

import com.sparta.toogo.domain.message.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    // 채팅방 입장 후, 메시지 작성하고, 해당 메시지를 Redis Topic 에 발행.  메시지를 발행하면, 대기 중이던 redis 구독 서비스가 메시지를 처리
    public void publish(ChannelTopic topic, MessageDto message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
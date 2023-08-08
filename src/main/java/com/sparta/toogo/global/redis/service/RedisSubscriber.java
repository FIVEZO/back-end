package com.sparta.toogo.global.redis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.toogo.domain.message.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {       // Redis 에 메시지가 발행될 때까지 대기하다가, 메시지가 발행되면 해당 메시지를 읽어서 처리해주는 리스너
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    // Redis 에서 메시지가 발행(publish)되면, 대기하고 있던 onMessage 가 해당 메시지를 받아 처리
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());        // redis 에서 발행된 데이터를 받아 역직렬화
            MessageDto roomMessage = objectMapper.readValue(publishMessage, MessageDto.class);                          // 해당 객체를 MessageDto 객체로 맵핑
            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);        // Websocket 구독자에게 채팅 메시지 전송
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
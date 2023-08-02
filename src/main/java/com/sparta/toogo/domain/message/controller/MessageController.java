package com.sparta.toogo.domain.message.controller;

import com.sparta.toogo.domain.message.dto.MessageDto;
import com.sparta.toogo.domain.message.redis.service.RedisPublisher;
import com.sparta.toogo.domain.messageroom.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {
    private final RedisPublisher redisPublisher;
    private final MessageRoomService messageRoomService;

    // websocket "/pub/chat/message"로 들어오는 메시지를 처리
    @MessageMapping("/chat/message")
    public void message(MessageDto message) {
        // 클라이언트가 쪽지방(topic) 입장 시, 대화 가능하도록 리스너와 연동
        messageRoomService.enterMessageRoom(message.getRoomId());

        // Websocket 에 발행된 메시지를 redis 로 발행(서로 다른 서버에 공유하기 위함)
        redisPublisher.publish(messageRoomService.getTopic(message.getRoomId()), message);
    }
}
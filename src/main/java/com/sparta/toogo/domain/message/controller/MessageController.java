package com.sparta.toogo.domain.message.controller;

import com.sparta.toogo.domain.message.dto.MessageDto;
import com.sparta.toogo.global.redis.service.RedisPublisher;
import com.sparta.toogo.domain.messageroom.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {
    private final RedisPublisher redisPublisher;
    private final MessageRoomService messageRoomService;

    // websocket "/pub/message"로 들어오는 메시지를 처리
    @MessageMapping("/message")
    public void message(MessageDto message) {

        // 클라이언트의 쪽지방(topic) 입장, 대화를 위해 리스너와 연동
        messageRoomService.enterMessageRoom(message.getRoomId());

        // Websocket 에 발행된 메시지를 redis 로 발행. 해당 쪽지방을 구독한 클라이언트에게 메시지가 실시간 전송됨 (1:N, 1:1 에서 사용 가능)
        redisPublisher.publish(messageRoomService.getTopic(message.getRoomId()), message);
    }
}
package com.sparta.toogo.domain.message.controller;

import com.sparta.toogo.domain.message.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")        // /pub/chat/message 로 메시지 발행을 처리
    public void message(MessageDto message) {
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);     // /sub/chat/room/{roomId} 로 메시지 전송
    }
}
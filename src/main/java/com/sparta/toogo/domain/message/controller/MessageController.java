package com.sparta.toogo.domain.message.controller;

import com.sparta.toogo.domain.message.dto.MessageDto;
import com.sparta.toogo.domain.message.dto.MessageResponseDto;
import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.message.service.MessageService;
import com.sparta.toogo.domain.messageroom.service.MessageRoomService;
import com.sparta.toogo.global.redis.service.RedisPublisher;
import com.sparta.toogo.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MessageController {
    private final RedisPublisher redisPublisher;
    private final MessageRoomService messageRoomService;
    private final MessageService messageService;

    // 대화 & 대화 저장
    @MessageMapping("/message")     // websocket "/pub/message" 로 들어오는 메시지를 처리
    @SendTo("/topic/message")
    public void message(MessageDto messageDto) {
        // 클라이언트의 쪽지방(topic) 입장, 대화를 위해 리스너와 연동
        messageRoomService.enterMessageRoom(messageDto.getRoomId());

        // Websocket 에 발행된 메시지를 redis 로 발행. 해당 쪽지방을 구독한 클라이언트에게 메시지가 실시간 전송됨 (1:N, 1:1 에서 사용 가능)
        redisPublisher.publish(messageRoomService.getTopic(messageDto.getRoomId()), messageDto);

        messageService.saveMessage(messageDto);
    }

    @Operation(summary = "대화 내역 조회", description = "특정 쪽지방에 저장된 채팅을 Redis 와 DB 에서 조회합니다.")
    @GetMapping("/api/room/{roomId}/message")
    public ResponseEntity<List<Message>> loadMessage(
            @Parameter(description = "roomId", required = true, example = "9e648d2d-5e2e-42b3-82fc-b8bef8111cbe")
            @PathVariable String roomId) {
        return ResponseEntity.ok(messageService.loadMessage(roomId));
    }

    // 메시지 작성 - 테스트용
    @PostMapping("/room/message/{roomId}")
    public MessageResponseDto createMessage(@PathVariable String roomId, @RequestBody MessageDto messageDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageService.createMessage(roomId, messageDto, userDetails.getUser());
    }
}
package com.sparta.toogo.domain.messageroom.controller;

import com.sparta.toogo.domain.message.service.MessageService;
import com.sparta.toogo.domain.messageroom.dto.MessageRoomDto;
import com.sparta.toogo.domain.messageroom.dto.MsgResponseDto;
import com.sparta.toogo.domain.messageroom.service.MessageRoomService;
import com.sparta.toogo.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/messageRoom")
public class MessageRoomController {
    private final MessageRoomService messageRoomService;

    // 쪽지방 생성
    @PostMapping
    public MessageRoomDto createRoom(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.createRoom(userDetails.getUser());
    }

    // 쪽지방 조회
    @GetMapping
    public List<MessageRoomDto> findAllRoom() {
        return messageRoomService.findAllRoom();
    }

    // 쪽지방 삭제
    @DeleteMapping("/{id}")
    public MsgResponseDto deleteRoom(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.deleteRoom(id, userDetails.getUser());
    }
}

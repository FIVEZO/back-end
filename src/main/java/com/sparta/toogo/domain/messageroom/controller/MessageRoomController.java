package com.sparta.toogo.domain.messageroom.controller;

import com.sparta.toogo.domain.message.dto.MessageRequestDto;
import com.sparta.toogo.domain.message.dto.MessageResponseDto;
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
@RequestMapping("/api")
public class MessageRoomController {
    private final MessageRoomService messageRoomService;

    @PostMapping("/room")
    public MessageResponseDto createRoom(@RequestBody MessageRequestDto messageRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.createRoom(messageRequestDto, userDetails.getUser());
    }

    @GetMapping("/rooms")
    public List<MessageResponseDto> findAllRoomByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.findAllRoomByUser(userDetails.getUser());
    }

    @GetMapping("room/{roomId}")
    public MessageRoomDto findRoom(@PathVariable String roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.findRoom(roomId, userDetails.getUser());
    }

    @DeleteMapping("room/{id}")
    public MsgResponseDto deleteRoom(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.deleteRoom(id, userDetails.getUser());
    }
}
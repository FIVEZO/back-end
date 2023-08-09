package com.sparta.toogo.domain.messageroom.controller;

import com.sparta.toogo.domain.message.dto.MessageRequestDto;
import com.sparta.toogo.domain.message.dto.MessageResponseDto;
import com.sparta.toogo.domain.messageroom.dto.MessageRoomDto;
import com.sparta.toogo.domain.messageroom.dto.MsgResponseDto;
import com.sparta.toogo.domain.messageroom.service.MessageRoomService;
import com.sparta.toogo.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MessageRoomController {
    private final MessageRoomService messageRoomService;

    // 쪽지방 생성 (receiver 에게 '쪽지 보내기'를 누를 시)
    @PostMapping("/room")
    public MessageResponseDto createRoom(@RequestBody MessageRequestDto messageRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.createRoom(messageRequestDto, userDetails.getUser());
    }

    // 사용자 관련 쪽지방 전체 조회
    @GetMapping("/rooms")
    public List<MessageResponseDto> findAllRoomByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.findAllRoomByUser(userDetails.getUser());
    }

    // 사용자 관련 쪽지방 선택 조회 (특정 쪽지방 입장)
//    @GetMapping("room/{id}")
//    public MessageRoomDto findRoom(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return messageRoomService.findRoom(id, userDetails.getUser());
//    }

    @GetMapping("/room/{roomId}")
    public void enterRoom(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
    }

    // 쪽지방 삭제
    @DeleteMapping("room/{id}")
    public MsgResponseDto deleteRoom(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.deleteRoom(id, userDetails.getUser());
    }
}
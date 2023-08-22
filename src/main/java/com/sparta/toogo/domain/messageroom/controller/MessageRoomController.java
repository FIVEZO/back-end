package com.sparta.toogo.domain.messageroom.controller;

import com.sparta.toogo.domain.message.dto.MessageRequestDto;
import com.sparta.toogo.domain.message.dto.MessageResponseDto;
import com.sparta.toogo.domain.messageroom.dto.MessageRoomDto;
import com.sparta.toogo.domain.messageroom.dto.MsgResponseDto;
import com.sparta.toogo.domain.messageroom.service.MessageRoomService;
import com.sparta.toogo.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MessageRoomController {
    private final MessageRoomService messageRoomService;

    @Operation(summary = "쪽지방 생성", description = "특정 쪽지방을 생성합니다.")
    @PostMapping("/room")
    public MessageResponseDto createRoom(@RequestBody MessageRequestDto messageRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.createRoom(messageRequestDto, userDetails.getUser());
    }

    @Operation(summary = "쪽지방 전체 조회", description = "사용자 자신과 관련된 쪽지방 목록을 조회합니다.")
    @GetMapping("/rooms")
    public List<MessageResponseDto> findAllRoomByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.findAllRoomByUser(userDetails.getUser());
    }

    @Operation(summary = "쪽지방 선택 조회", description = "사용자 자신과 관련된 쪽지방을 선택 조회합니다.")
    @GetMapping("room/{roomId}")
    public MessageRoomDto findRoom(
            @Parameter(description = "roomId", required = true, example = "9e648d2d-5e2e-42b3-82fc-b8bef8111cbe") @PathVariable String roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.findRoom(roomId, userDetails.getUser());
    }

    @Operation(summary = "쪽지방 삭제", description = "특정 쪽지방을 삭제합니다.")
    @DeleteMapping("room/{id}")
    public MsgResponseDto deleteRoom(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return messageRoomService.deleteRoom(id, userDetails.getUser());
    }
}
package com.sparta.toogo.domain.message.dto;

import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageResponseDto {
    private Long id;
    private String roomName;
    private String sender;
    private String roomId;
    private String receiver;
    private String message;

    // 쪽지방 생성
    public MessageResponseDto(MessageRoom messageRoom) {
        this.id = messageRoom.getId();
        this.roomName = messageRoom.getRoomName();
        this.sender = messageRoom.getSender();
        this.roomId = messageRoom.getRoomId();
        this.receiver = messageRoom.getReceiver();
    }

    // 사용자 관련 쪽지방 전체 조회
    public MessageResponseDto(Long id, String roomName, String roomId, String sender, String receiver) {
        this.id = id;
        this.roomName = roomName;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }
}
package com.sparta.toogo.domain.message.dto;

import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageResponseDto {
    private Long id;
    private String sender;
    private String roomId;
    private String receiver;
    private String message;

    // 쪽지방 생성
    public MessageResponseDto(MessageRoom messageRoom) {
        this.id = messageRoom.getId();
        this.sender = messageRoom.getSender();
        this.roomId = messageRoom.getRoomId();
        this.receiver = messageRoom.getReceiver();
    }

    // 사용자 관련 쪽지방 전체 조회
    public MessageResponseDto(Long id, String roomId, String sender, String receiver) {
        this.id = id;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }

    // 대화 조회 - 일단 DB 에서
    public MessageResponseDto(Message saveMessage) {
        this.sender = saveMessage.getSender();
        this.roomId = saveMessage.getRoomId();
        this.receiver = saveMessage.getReceiver();
        this.message = saveMessage.getMessage();
    }

    public MessageResponseDto(MessageDto messageDto) {
        this.sender = messageDto.getSender();
        this.roomId = messageDto.getRoomId();
        this.receiver = messageDto.getReceiver();
        this.message = messageDto.getMessage();
    }

    public MessageResponseDto(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Long getId() {
        return id;
    }
}
package com.sparta.toogo.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseDto {
    private Long id;
    private String roomName;
    private String sender;
    private Long senderId;
    private String roomId;
    private String receiver;
    private Long receiverId;
    private Long postId;
    private String message;
    private LocalDateTime createdAt;
    private String emoticon;

    // 쪽지방 생성
    public MessageResponseDto(MessageRoom messageRoom) {
        this.id = messageRoom.getId();
        this.roomName = messageRoom.getRoomName();
        this.senderId = messageRoom.getUser().getId();
        this.roomId = messageRoom.getRoomId();
        this.receiverId = messageRoom.getReceiverId();
        this.postId = messageRoom.getPost().getId();
    }

    // 대화 저장 - 테스트용
    public MessageResponseDto(Message saveMessage) {
        this.id = saveMessage.getId();
        this.sender = saveMessage.getSender();
        this.roomId = saveMessage.getRoomId();
        this.receiver = saveMessage.getReceiver();
        this.createdAt = saveMessage.getCreatedAt();
    }

    // 쪽지방 생성 - 중복 방지
    public MessageResponseDto(String roomId) {
        this.roomId = roomId;
    }

    // 사용자 관련 쪽지방 전체 조회
    public MessageResponseDto(Long id, String roomId, LocalDateTime createdAt) {
        this.id = id;
        this.roomId = roomId;

        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = createdAt.atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
    }

    public void setLatestMessageContent(String message) {
        this.message = message;
    }

    public void setLatestMessageCreatedAt(LocalDateTime createdAt) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = createdAt.atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
    }

    public void setRoomName(String nickname) {
        this.roomName = nickname;
    }

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }
}
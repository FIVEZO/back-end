package com.sparta.toogo.domain.message.dto;

import com.sparta.toogo.domain.message.entity.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto extends Message {
    private Long senderId;
    private String sender;
    private String roomId;
    private String receiver;
    private String message;
    private String sentTime;
    private LocalDateTime createdAt;        // 최신 메시지 전송 시간

    // 대화 조회
    public MessageDto(Long senderId, String sender, String roomId, String message, String sentTime) {
        this.senderId = senderId;
        this.sender = sender;
        this.roomId = roomId;
        this.message = message;
        this.sentTime = sentTime;
    }
}
package com.sparta.toogo.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto implements Serializable {
    private String sender;
    private String roomId;
    private String receiver;
    private String message;
    private String sentTime;
    private LocalDateTime createdAt;        // 최신 메시지 전송 시간

    public MessageDto(String sender, String roomId, String receiver, String message, String sentTime) {
        this.sender = sender;
        this.roomId = roomId;
        this.receiver = receiver;
        this.message = message;
        this.sentTime = sentTime;
    }
}
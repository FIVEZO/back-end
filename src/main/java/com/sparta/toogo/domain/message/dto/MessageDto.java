package com.sparta.toogo.domain.message.dto;

import com.sparta.toogo.domain.message.entity.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private String sender;
    private String roomId;
//    private String receiver;
    private String message;
    private String sentTime;
    private LocalDateTime createdAt;        // 최신 메시지 전송 시간

    public MessageDto(Message message) {
        this.sender = message.getSender();
        this.roomId = message.getRoomId();
//        this.receiver = message.getReceiver();
        this.message = message.getMessage();
        this.sentTime = message.getSentTime();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = message.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
    }
}
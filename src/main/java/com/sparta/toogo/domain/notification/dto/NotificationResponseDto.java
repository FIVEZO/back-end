package com.sparta.toogo.domain.notification.dto;

import com.sparta.toogo.domain.notification.entity.Notification;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationResponseDto {
    private Long id;
    private String sender;
    private LocalDateTime createdAt;
    private boolean readStatus;
    private String contents;        // 채팅 메시지 내용 또는 댓글 내용
    private String message;
    private String emoticon;

    public NotificationResponseDto(Long id, String sender, LocalDateTime createdAt, String contents, String emoticon, String message) {
        this.id = id;
        this.sender = sender;
        this.createdAt = createdAt;
        this.readStatus = false;
        this.contents = contents;
        this.message = message;
        this.emoticon = emoticon;
    }
}

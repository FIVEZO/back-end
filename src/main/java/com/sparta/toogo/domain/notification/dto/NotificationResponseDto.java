package com.sparta.toogo.domain.notification.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationResponseDto {
    private Long id;
    private String sender;
    private LocalDateTime createdAt;
    private boolean readStatus;
    private String contents; // 채팅 메시지 내용 또는 댓글 내용
    private String message;
    private String emoticon;
    private String roomId;
    private Long postId;
    private Long category;

    // 알림 목록 조회 - 댓글 알림일 경우
    public NotificationResponseDto(Long id, String sender, LocalDateTime createdAt, String contents, String emoticon, String message, Long id1, Long value) {
        this.id = id;
        this.sender = sender;
        this.createdAt = createdAt;
        this.readStatus = false;
        this.contents = contents;
        this.message = message;
        this.emoticon = emoticon;
        this.postId = id1;
        this.category = value;
    }

    // 알림 목록 조회 - 쪽지방 생성 알림일 경우
    public NotificationResponseDto(Long id, String sender, LocalDateTime createdAt, String contents, String emoticon, String message, String roomId) {
        this.id = id;
        this.sender = sender;
        this.createdAt = createdAt;
        this.readStatus = false;
        this.contents = contents;
        this.message = message;
        this.emoticon = emoticon;
        this.roomId = roomId;
    }
}
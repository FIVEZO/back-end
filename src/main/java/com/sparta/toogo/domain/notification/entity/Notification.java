package com.sparta.toogo.domain.notification.entity;

import com.sparta.toogo.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "notification")
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    private LocalDateTime createdAt;

    private String contents;        // 채팅 메시지 내용 또는 댓글 내용

    private String roomId;

    private boolean readStatus;

    private Long userId;

    private String message;

    private String emoticon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
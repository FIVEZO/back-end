package com.sparta.toogo.domain.comment.entity;

import com.sparta.toogo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String content;
    private LocalDateTime createdAt;

    public Notification(User user, NotificationType notificationType, String content) {
        this.user = user;
        this.type = notificationType;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}

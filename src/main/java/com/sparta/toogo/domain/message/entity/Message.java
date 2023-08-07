package com.sparta.toogo.domain.message.entity;

import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "message")
@NoArgsConstructor
public class Message extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messageRoomId", nullable = false)
    private MessageRoom messageRoom;

//    @ManyToOne
//    @JoinColumn(name = "mypageId", nullable = false)
//    private Mypage mypage;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}

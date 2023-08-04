package com.sparta.toogo.domain.messageroom.entity;

import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "messageRoom")
@NoArgsConstructor
public class MessageRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    @Column(unique = true)
    private String roomId;
    private String receiver;        // 메시지 수신자

    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.REMOVE)
    private List<Message> messageList = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mypageId", nullable = false)
//    private Mypage mypage;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // 쪽지방 생성
    public MessageRoom(Long id, String sender, String roomId, String receiver, User user) {
        this.id = id;
        this.sender = sender;
        this.roomId = roomId;
        this.receiver = receiver;
        this.user = user;
    }
}
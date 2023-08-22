package com.sparta.toogo.domain.messageroom.entity;

import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
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
    private String roomName;
    private String sender;
    @Column(unique = true)
    private String roomId;
    private String receiver;        // 메시지 수신자

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mypageId", nullable = false)
//    private Mypage mypage;

    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.REMOVE)
    private List<Message> messageList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // 쪽지방 생성
    public MessageRoom(Long id, String roomName, String sender, String roomId, String receiver, User user, Post post) {
        this.id = id;
        this.roomName = roomName;
        this.sender = sender;
        this.roomId = roomId;
        this.receiver = receiver;
        this.user = user;
        this.post = post;
    }
}
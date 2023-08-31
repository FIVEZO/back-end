package com.sparta.toogo.domain.messageroom.entity;

import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.util.Timestamped;
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
public class MessageRoom extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;
    private String sender;
    @Column(unique = true)
    private String roomId;
    private Long receiverId;        // 수신자의 userId

    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.REMOVE)
    private List<Message> messageList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // 쪽지방 생성
    public MessageRoom(Long id, String roomName, String sender, String roomId, Long receiverId, User user, Post post) {
        super();
        this.id = id;
        this.roomName = roomName;
        this.sender = sender;
        this.roomId = roomId;
        this.receiverId = receiverId;
        this.user = user;
        this.post = post;
    }
}
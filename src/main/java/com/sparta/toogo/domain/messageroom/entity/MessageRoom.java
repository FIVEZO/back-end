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
    private String name;
    @Column(unique = true)
    private String roomId;

    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.REMOVE)
    private List<Message> messageList = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mypageId", nullable = false)
//    private Mypage mypage;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public MessageRoom(String name, String roomId, User user) {
        this.name = name;
        this.roomId = roomId;
        this.user = user;
    }
}

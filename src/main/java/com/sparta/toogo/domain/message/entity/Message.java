package com.sparta.toogo.domain.message.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@Table(name = "message")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message extends Timestamped implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sender")
    private String sender;
    @Column(name = "roomId")
    private String roomId;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "message")
    private String message;

    @Column(name = "sentTime")
    private String sentTime;

    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "roomId", insertable = false, updatable = false)
    private MessageRoom messageRoom;

    @Column(name = "senderId")
    private Long senderId;

//    @ManyToOne
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;
// @ManyToOne
// @JoinColumn(name = "mypageId", nullable = false)
// private Mypage mypage;

    // 대화 저장
    public Message(Long id, String sender, String roomId, String message, String sentTime) {
        super();
        this.senderId = id;
        this.sender = sender;
        this.roomId = roomId;
        this.message = message;
        this.sentTime = sentTime;
    }

    public Message(String sender, String roomId, String message, String sentTime) {
        super();
        this.sender = sender;
        this.roomId = roomId;
        this.message = message;
        this.sentTime = sentTime;
    }
}
package com.sparta.toogo.domain.message.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "message")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sender")
    private String sender;
    @Column(name = "roomId")
    private String roomId;
    @Column(name = "receiverId")
    private Long receiverId;
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

    // 대화 저장
    public Message(Long id, String sender, String roomId, Long id1, String message, String sentTime) {
        super();
        this.senderId = id;
        this.sender = sender;
        this.roomId = roomId;
        this.receiverId = id1;
        this.message = message;
        this.sentTime = sentTime;
    }
}
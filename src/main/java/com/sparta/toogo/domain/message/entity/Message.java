package com.sparta.toogo.domain.message.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.toogo.domain.message.dto.MessageDto;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message extends Timestamped {
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "messageRoom", nullable = false)
//    private MessageRoom messageRoom;

    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "roomId", insertable = false, updatable = false)
    private MessageRoom messageRoom;

//    @ManyToOne
//    @JoinColumn(name = "mypageId", nullable = false)
//    private Mypage mypage;

//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private User user;

    // 대화 저장
    public Message(MessageDto messageDto) {
        super();
        this.sender = messageDto.getSender();
        this.roomId = messageDto.getRoomId();
        this.receiver = messageDto.getReceiver();
        this.message = messageDto.getMessage();
    }

    // 대화 저장 - 테스트용
    public Message(String sender, String roomId, String receiver, String message, MessageRoom messageRoom, User user) {
        this.sender = sender;
        this.roomId = roomId;
        this.receiver = receiver;
        this.message = message;
        this.messageRoom = messageRoom;
//        this.user = user;
    }

    public Message(String sender, String roomId, String receiver, String message) {
        super();
        this.sender = sender;
        this.roomId = roomId;
        this.receiver = receiver;
        this.message = message;
    }
}
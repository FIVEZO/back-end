package com.sparta.toogo.domain.message.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.toogo.domain.message.dto.MessageDto;
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

    // 대화 저장
    public Message(MessageDto messageDto) {
        super();
        this.sender = messageDto.getSender();
        this.roomId = messageDto.getRoomId();
        this.receiver = messageDto.getReceiver();
        this.message = messageDto.getMessage();
    }
}
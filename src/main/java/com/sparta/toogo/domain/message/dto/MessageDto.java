package com.sparta.toogo.domain.message.dto;

import com.sparta.toogo.domain.message.entity.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private String sender;
    private String roomId;
    private String receiver;
    private String message;
    private String sentTime;

    public MessageDto(Message message) {
        this.sender = message.getSender();
        this.roomId = message.getRoomId();
        this.receiver = message.getReceiver();
        this.message = message.getMessage();
    }
}
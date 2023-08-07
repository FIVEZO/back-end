package com.sparta.toogo.domain.message.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private String sender;
    private String roomId;
    private String receiver;
    private String message;
}
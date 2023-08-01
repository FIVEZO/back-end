package com.sparta.toogo.domain.messageroom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageRoomDto {
    private String roomId;
    private String name;

    @Builder
    public MessageRoomDto(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }
}

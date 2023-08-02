package com.sparta.toogo.domain.messageroom.dto;

import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRoomDto {
    private String roomId;
    private String name;

    @Builder
    public MessageRoomDto(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public MessageRoomDto(MessageRoom messageRoom) {
        this.roomId = messageRoom.getRoomId();
        this.name = messageRoom.getName();
    }
}

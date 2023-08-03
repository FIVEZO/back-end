package com.sparta.toogo.domain.messageroom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.user.entity.User;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageRoomDto implements Serializable {       // Redis 에 저장되는 객체들이 직렬화가 가능하도록

    private static final long serialVersionUID = 6494678977089006639L;      // 역직렬화 위한 serialVersionUID 세팅
    private Long id;
    private String roomId;
    private String name;

    // 쪽지방 전체 조회
    public MessageRoomDto(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    // 쪽지방 선택 조회
    public MessageRoomDto(MessageRoom messageRoom) {
        this.id = messageRoom.getId();
        this.roomId = messageRoom.getRoomId();
        this.name = messageRoom.getName();
    }

    // 쪽지방 생성
    public static MessageRoomDto create(User user) {
        MessageRoomDto messageRoomDto = new MessageRoomDto();
        messageRoomDto.id = messageRoomDto.getId();
        messageRoomDto.roomId = UUID.randomUUID().toString();
        messageRoomDto.name = user.getNickname();

        return messageRoomDto;
    }
}

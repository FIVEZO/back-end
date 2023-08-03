package com.sparta.toogo.domain.messageroom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.toogo.domain.message.dto.MessageRequestDto;
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
    private String sender;     // 메시지 송신자
    private String receiver;   // 메시지 수신자

    // 쪽지방 생성
    public static MessageRoomDto create(MessageRequestDto messageRequestDto, User user) {
        MessageRoomDto messageRoomDto = new MessageRoomDto();
        messageRoomDto.roomId = UUID.randomUUID().toString();
        messageRoomDto.sender = user.getNickname();
        messageRoomDto.receiver = messageRequestDto.getReceiver();

        return messageRoomDto;
    }
}

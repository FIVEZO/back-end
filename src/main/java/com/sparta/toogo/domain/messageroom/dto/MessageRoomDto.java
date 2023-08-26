package com.sparta.toogo.domain.messageroom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.toogo.domain.message.dto.MessageRequestDto;
import com.sparta.toogo.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String roomName;
    private String roomId;
    private String sender;     // 메시지 송신자
    private String receiver;   // 메시지 수신자
    private Long postId;
    private Long category;      // 게시글 카테고리
    private String title;       // 게시글 제목
    private String country;     // 게시글 나라

    // 쪽지방 생성
    public static MessageRoomDto create(MessageRequestDto messageRequestDto, User user) {
        MessageRoomDto messageRoomDto = new MessageRoomDto();
        messageRoomDto.roomName = messageRequestDto.getReceiver();
        messageRoomDto.roomId = UUID.randomUUID().toString();
        messageRoomDto.sender = user.getNickname();
        messageRoomDto.receiver = messageRequestDto.getReceiver();

        return messageRoomDto;
    }

    // 사용자 관련 쪽지방 선택 조회
    public MessageRoomDto(Long id, String roomName, String roomId, String sender, String receiver) {
        this.id = id;
        this.roomName = roomName;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void setMessageRoomPostId(Long postId) {
        this.postId = postId;
    }

    public void setMessageRoomCategory(Long category) {
        this.category = category;
    }

    public void setMessageRoomTitle(String title) {
        this.title = title;
    }

    public void setMessageRoomCountry(String country) {
        this.country = country;
    }


}
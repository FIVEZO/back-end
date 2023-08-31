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
    private Long receiverId;
    private Long postId;
    private Long category;      // 게시글 카테고리
    private String title;       // 게시글 제목
    private String country;     // 게시글 나라
    private String emoticon;
    private Long myId;

    // 쪽지방 생성
    public static MessageRoomDto create(User user, User userReceiver) {
        MessageRoomDto messageRoomDto = new MessageRoomDto();
        messageRoomDto.roomName = userReceiver.getNickname();
        messageRoomDto.roomId = UUID.randomUUID().toString();
        messageRoomDto.sender = user.getNickname();
        messageRoomDto.receiverId = userReceiver.getId();

        return messageRoomDto;
    }

    // 사용자 관련 쪽지방 선택 조회 - user 가 sender 인 경우
    public MessageRoomDto(Long id, String roomId, Long myId, String nickname) {
        this.id = id;
        this.roomId = roomId;
        this.roomName = nickname;
        this.myId = myId;
    }

    // 사용자 관련 쪽지방 선택 조회 - user 가 receiver 인 경우
    public MessageRoomDto(Long id, Long myId, String roomId) {
        this.id = id;
        this.roomId = roomId;
        this.myId = myId;
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

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }
}
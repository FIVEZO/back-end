package com.sparta.toogo.domain.messageroom.service;

import com.sparta.toogo.domain.messageroom.dto.MessageRoomDto;
import com.sparta.toogo.domain.messageroom.dto.MsgResponseDto;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.user.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageRoomService {
    private final MessageRoomRepository messageRoomRepository;

    private Map<String, MessageRoomDto> messageRoomDtoList;

    @PostConstruct
    private void init() {
        messageRoomDtoList = new LinkedHashMap<>();
    }

    // 쪽지방 조회
    public List<MessageRoomDto> findAllRoom() {
        List<MessageRoom> messageRooms = messageRoomRepository.findAll();
        List<MessageRoomDto> messageRoomDtos = new ArrayList<>();

        for (MessageRoom messageRoom : messageRooms) {
            MessageRoomDto messageRoomDto = new MessageRoomDto(messageRoom.getRoomId(), messageRoom.getName());
            messageRoomDtos.add(messageRoomDto);
        }

        Collections.reverse(messageRoomDtos);

        return messageRoomDtos;
    }

    public MessageRoomDto findRoomById(String roomId) {
        return messageRoomDtoList.get(roomId);
    }

    // 쪽지방 생성
    public MessageRoomDto createRoom(User user) {
        String randomId = UUID.randomUUID().toString();

        MessageRoomDto messageRoomDto = MessageRoomDto.builder()
                .roomId(randomId)
                .name(user.getNickname())
                .build();

        messageRoomDtoList.put(randomId, messageRoomDto);
        messageRoomRepository.save(new MessageRoom(messageRoomDto.getName(), messageRoomDto.getRoomId(), user));

        return messageRoomDto;
    }

    // 쪽지방 삭제
    public MsgResponseDto deleteRoom(Long id, User user) {
        MessageRoom messageRoom = messageRoomRepository.findByIdAndUser(id, user);

        messageRoomRepository.delete(messageRoom);
        return new MsgResponseDto("채팅방을 삭제했습니다.", HttpStatus.OK.value());
    }
}

package com.sparta.toogo.domain.message.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.toogo.domain.messageroom.dto.MessageRoomDto;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.user.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {
    private final ObjectMapper objectMapper;
    private Map<String, MessageRoomDto> messageRoomDtoList;
    private final MessageRoomRepository messageRoomRepository;

    @PostConstruct
    private void init() {
         messageRoomDtoList = new LinkedHashMap<>();
    }

    public List<MessageRoomDto> findAllRoom() {
        return new ArrayList<>(messageRoomDtoList.values());
    }

    public MessageRoomDto findRoomById(String roomId) {
        return messageRoomDtoList.get(roomId);
    }

    // 채팅방 생성
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

    // 채팅방의 webSocket session 에 메시지 전송
    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
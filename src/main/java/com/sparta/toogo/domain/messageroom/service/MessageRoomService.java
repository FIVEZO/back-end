package com.sparta.toogo.domain.messageroom.service;

import com.sparta.toogo.domain.message.redis.service.RedisSubscriber;
import com.sparta.toogo.domain.messageroom.dto.MessageRoomDto;
import com.sparta.toogo.domain.messageroom.dto.MsgResponseDto;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.user.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageRoomService {
    private final MessageRoomRepository messageRoomRepository;

    // 쪽지방(topic)에 발행되는 메시지 처리하는 리스너
    private final RedisMessageListenerContainer redisMessageListener;

    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    // redis
    private static final String Message_Rooms = "MESSAGE_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, MessageRoomDto> opsHashMessageRoom;

    // 쪽지방의 대화 메시지 발행을 위한 redis topic 정보
    private Map<String, ChannelTopic> topics;       // 서버별로 쪽지방에 매치되는 topic 정보를 Map 에 넣어서, roomId 로 찾음

    @PostConstruct
    private void init() {
        opsHashMessageRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    // 쪽지방 생성
    public MessageRoomDto createRoom(User user) {
        MessageRoomDto messageRoomDto = MessageRoomDto.create(user);
        opsHashMessageRoom.put(Message_Rooms, messageRoomDto.getRoomId(), messageRoomDto);      // 서버간 채팅방 공유를 위해, redis hash 에 저장
        messageRoomRepository.save(new MessageRoom(messageRoomDto.getName(), messageRoomDto.getRoomId(), user));

        return messageRoomDto;
    }

    // 쪽지방 전체 조회
    public List<MessageRoomDto> findAllRoom() {
        List<MessageRoom> messageRooms = messageRoomRepository.findAll();
        List<MessageRoomDto> messageRoomDtos = new ArrayList<>();
        for (MessageRoom messageRoom : messageRooms) {
            MessageRoomDto messageRoomDto = new MessageRoomDto(messageRoom.getRoomId(), messageRoom.getName());
            messageRoomDtos.add(messageRoomDto);
        }

        return messageRoomDtos;
    }

//    public MessageRoomDto findRoomById(String id) {
//        return opsHashMessageRoom.get(Message_Rooms, id);
//    }

    // 쪽지방 선택 조회
    public MessageRoomDto findRoom(Long id) {
        MessageRoom messageRoom = messageRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("쪽지방이 존재하지 않습니다.")
        );

        return new MessageRoomDto(messageRoom);
    }

    // 쪽지방 삭제
    public MsgResponseDto deleteRoom(Long id, User user) {
        MessageRoom messageRoom = messageRoomRepository.findByIdAndUser(id, user);
        messageRoomRepository.delete(messageRoom);
        return new MsgResponseDto("쪽지방을 삭제했습니다.", HttpStatus.OK.value());
    }

    // 쪽지방 입장 : redis 에 topic 생성 후, pub/sub 통신을 하기 위해 리스너를 설정
    public void enterMessageRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
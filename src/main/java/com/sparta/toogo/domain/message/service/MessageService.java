package com.sparta.toogo.domain.message.service;

import com.sparta.toogo.domain.message.dto.MessageDto;
import com.sparta.toogo.domain.message.dto.MessageResponseDto;
import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.message.repository.MessageRepository;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final RedisTemplate<String, MessageDto> redisTemplateMessage;
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;

    // 대화 저장
    public void saveMessage(MessageDto messageDto) {
        // DB 저장
//        Message message = new Message(messageDto.getSender(), messageDto.getRoomId(), messageDto.getReceiver(), messageDto.getMessage());
        Message message = new Message(messageDto.getSender(), messageDto.getRoomId(), messageDto.getMessage());
        messageRepository.save(message);

        // 직렬화
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));

        // redis 저장
        redisTemplateMessage.opsForList().rightPush(messageDto.getRoomId(), messageDto);

        // 스케줄링 기능 (1시간 마다)
        redisTemplateMessage.expire(messageDto.getRoomId(), 1, TimeUnit.HOURS);
    }

    // 대화 조회 - Redis & DB
    public List<MessageDto> loadMessage(String roomId) {
        List<MessageDto> messageList = new ArrayList<>();

        // Redis 에서 해당 채팅방의 메시지 100개 가져오기
        List<MessageDto> redisMessageList = redisTemplateMessage.opsForList().range(roomId, 0, 99);

        // Redis 에서 가져온 메시지가 없다면, DB 에서 메시지 100개 가져오기
        if (redisMessageList == null || redisMessageList.isEmpty()) {
            List<Message> dbMessageList = messageRepository.findTop100ByRoomIdOrderByCreatedAtAsc(roomId);
            for (Message message : dbMessageList) {
                messageList.add(new MessageDto(message));
            }
        } else {
            messageList.addAll(redisMessageList);
        }

        return messageList;
    }

    // 대화 저장 - 테스트용
//    public MessageResponseDto createMessage(String roomId, MessageDto messageDto, User user) {
////        MessageRoom messageRoom = messageRoomRepository.findById(id).orElseThrow(
////                () -> new IllegalArgumentException("쪽지방이 존재하지 않습니다.")
////        );
//
//        // DB 저장
////        Message message = new Message(messageDto.getSender(), messageDto.getRoomId(), messageDto.getReceiver(), messageDto.getMessage());
//        Message message = new Message(messageDto.getSender(), messageDto.getRoomId(), messageDto.getMessage());
//        Message saveMessage = messageRepository.save(message);
//
//        // 직렬화
//        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));
//
//        // redis 저장
//        redisTemplateMessage.opsForList().rightPush(messageDto.getRoomId(), messageDto);
//
//        // 스케줄링 기능 (1시간 마다)
//        redisTemplateMessage.expire(messageDto.getRoomId(), 1, TimeUnit.HOURS);
//
//        return new MessageResponseDto(saveMessage);
//    }
}
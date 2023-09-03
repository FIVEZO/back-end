package com.sparta.toogo.domain.message.service;

import com.sparta.toogo.domain.message.dto.MessageDto;
import com.sparta.toogo.domain.message.dto.MessageResponseDto;
import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.message.repository.MessageRepository;
import com.sparta.toogo.domain.notification.service.NotificationService;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, Message> redisTemplateMessage;
    private final MessageRepository messageRepository;
    private final NotificationService notificationService;

    // 대화 저장
    public void saveMessage(MessageDto messageDto) {
        // DB 저장
        User userSender = userRepository.findByNickname(messageDto.getSender());
        User userReceiver = userRepository.findByNickname(messageDto.getReceiver());

        Message message = new Message(
                userSender.getId(),
                messageDto.getSender(),
                messageDto.getRoomId(),
                userReceiver.getId(),
                messageDto.getMessage(),
                messageDto.getSentTime());

        messageRepository.save(message);

        // Redis 에 message 객체를 저장하기 위해 직렬화 설정
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));

        // Redis 에 message 저장
        redisTemplateMessage.opsForList().rightPush(messageDto.getRoomId(),
                new Message(message.getSenderId(), messageDto.getSender(), messageDto.getRoomId(), message.getReceiverId(), messageDto.getMessage(), messageDto.getSentTime()));

        // 스케줄링 기능 (1시간 마다)
        redisTemplateMessage.expire(messageDto.getRoomId(), 1, TimeUnit.HOURS);

        // 알림 기능
//        notificationService.notifyMessage(messageDto.getRoomId(), userReceiver.getId(), userSender.getId());
    }

    // 대화 조회 - Redis & DB
    public List<Message> loadMessage(String roomId) {
        List<Message> messageList = new ArrayList<>();

        // Redis 에서 해당 채팅방의 메시지 100개 가져오기
        List<Message> redisMessageList = redisTemplateMessage.opsForList().range(roomId, 0, 99);

        // Redis 에서 가져온 메시지가 없다면, DB 에서 메시지 100개 가져오기
        if (redisMessageList == null || redisMessageList.isEmpty()) {
            List<Message> dbMessageList = messageRepository.findTop100ByRoomIdOrderByCreatedAtAsc(roomId);
            for (Message message : dbMessageList) {

                MessageDto messageDto = new MessageDto(message.getSenderId(), message.getSender(), message.getRoomId(), message.getMessage(), message.getSentTime());
                messageList.add(messageDto);
                redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));      // 직렬화
                redisTemplateMessage.opsForList().rightPush(roomId, messageDto);                                // redis 저장
            }
        } else {
            messageList.addAll(redisMessageList);
        }

        return messageList;
    }

    // 대화 삭제
    public void deleteMessageList(String roomId) {
        redisTemplateMessage.delete(roomId);
    }

    // 대화 저장 - 테스트용
    public MessageResponseDto createMessage(String roomId, MessageDto messageDto, User user) {
        // DB 저장
        User userSender = userRepository.findByNickname(messageDto.getSender());
        User userReceiver = userRepository.findByNickname(messageDto.getReceiver());

        Message message = new Message(
                userSender.getId(),
                messageDto.getSender(),
                messageDto.getRoomId(),
                userReceiver.getId(),
                messageDto.getMessage(),
                messageDto.getSentTime());

        Message saveMessage = messageRepository.save(message);

        // 직렬화
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));

        // redis 저장
        redisTemplateMessage.opsForList().rightPush(messageDto.getRoomId(),
                new Message(message.getSenderId(), messageDto.getSender(), messageDto.getRoomId(), message.getReceiverId(), messageDto.getMessage(), messageDto.getSentTime()));

        // 스케줄링 기능 (1시간 마다)
        redisTemplateMessage.expire(messageDto.getRoomId(), 1, TimeUnit.HOURS);

        // 알림 기능
//        notificationService.notifyMessage(messageDto.getRoomId(), userReceiver.getId(), userSender.getId());

        return new MessageResponseDto(saveMessage);
    }
}
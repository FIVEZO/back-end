package com.sparta.toogo.domain.message.service;

import com.sparta.toogo.domain.message.dto.MessageDto;
import com.sparta.toogo.domain.message.dto.MessageResponseDto;
import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.message.repository.MessageRepository;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final RedisTemplate<String, MessageDto> redisTemplateMessage;
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;

    // 대화 저장
    public void saveMessage(Long id, MessageDto messageDto, User user) {
        MessageRoom messageRoom = messageRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("쪽지방이 존재하지 않습니다.")
        );

        // DB 저장
        Message message = new Message(messageDto.getSender(), messageDto.getRoomId(), messageDto.getReceiver(), messageDto.getMessage(), messageRoom, user);
        messageRepository.save(message);

        // redis 저장 (roomId 를 Key 값으로)
        redisTemplateMessage.opsForList().rightPush(messageDto.getRoomId(), messageDto);        // list 로 redis 에 저장
    }

    // 대화 조회 - DB 에서
    public List<MessageResponseDto> loadMessage(String roomId) {
        List<Message> messageDtoList = messageRepository.findByRoomId(roomId);

        List<MessageResponseDto> messageResponseDtoList = new ArrayList<>();

        for (Message message : messageDtoList) {
            messageResponseDtoList.add(new MessageResponseDto(message));
        }

        return messageResponseDtoList;
    }
    
    // 대화 조회 - redis 에서
//    public List<MessageResponseDto> loadMessage(String roomId) {
//        List<MessageDto> messageDtoList = redisTemplateMessage.opsForList().range("roomId", 0, 99);

    // 대화 조회 - 일단 redis 에서 --> object(Json 객체) 가 string 으로 역직렬화 되지 않는 문제
    //        string 과 object 같이 받아와서
    // redis -> 객체
    // 객체 -> json 으로
//    public List<MessageResponseDto> loadMessage(String roomId) throws JsonProcessingException {
////        MessageRoom messageRoom = messageRoomRepository.findAllById(id).orElseThrow(
////                () -> new IllegalArgumentException("쪽지방이 존재하지 않습니다.")
////        );
//
////        List<MessageDto> messageDtoList = redisTemplateMessage.opsForList().range(messageRoom.getRoomId(), 0, 99);
//        List<MessageDto> messageDtoList = redisTemplateMessage.opsForList().range(roomId, 0, 99);
//
//        List<MessageResponseDto> messageResponseDtoList = new ArrayList<>();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        for (MessageDto messageDto : messageDtoList) {
//            String messageDtoMapper = objectMapper.writeValueAsString(messageDto);      // 객체 -> json
//            MessageResponseDto messageResponseDto = objectMapper.readValue(messageDtoMapper, MessageResponseDto.class);     // json -> 객체
//
//            messageResponseDtoList.add(messageResponseDto);
////            messageResponseDtoList.add(new MessageResponseDto(messageDto));
//        }
//
//        return messageResponseDtoList;
//    }

//    public List<MessageResponseDto> loadMessage(String roomId) throws JsonProcessingException {
//        List<MessageDto> messageDtoList = redisTemplateMessage.opsForList().range(roomId, 0, 99);
//
//        List<MessageResponseDto> messageResponseDtoList = new ArrayList<>();
//
//        for (MessageDto message : messageDtoList) {
//            messageResponseDtoList.add(new MessageResponseDto(message));
//        }
//
//        return messageResponseDtoList;
//    }

    // redis 대화방 삭제 (DB 에만 남아있게 됨)
    @Scheduled(cron = "0 0 */3 * * *")      // 3시간 마다
    @Transactional
    public void deleteRedis() {
        log.info("Scheduling start");

        redisTemplateMessage.delete("MESSAGE_ROOM");

        log.info("Scheduling done");
    }


    // 대화 저장 - 테스트용
//    public MessageResponseDto createMessage(Long id, MessageDto messageDto, User user) {
//        MessageRoom messageRoom = messageRoomRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("쪽지방이 존재하지 않습니다.")
//        );
//
//        Message message = new Message(messageDto.getSender(), messageDto.getRoomId(), messageDto.getReceiver(), messageDto.getMessage(), messageRoom, user);
////        Message message = new Message(messageDto);
//        Message saveMessage = messageRepository.save(message);
//
//        redisTemplateMessage.opsForList().rightPush(messageDto.getRoomId(), messageDto);        // list 로 redis 에 저장
//
//        return new MessageResponseDto(saveMessage);
//    }
}
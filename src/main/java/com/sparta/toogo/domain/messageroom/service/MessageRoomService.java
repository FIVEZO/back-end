package com.sparta.toogo.domain.messageroom.service;

import com.sparta.toogo.domain.message.dto.MessageRequestDto;
import com.sparta.toogo.domain.message.dto.MessageResponseDto;
import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.message.repository.MessageRepository;
import com.sparta.toogo.domain.message.service.MessageService;
import com.sparta.toogo.domain.messageroom.dto.MessageRoomDto;
import com.sparta.toogo.domain.messageroom.dto.MsgResponseDto;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.notification.service.NotificationService;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.repository.UserRepository;
import com.sparta.toogo.global.redis.service.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageRoomService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MessageRoomRepository messageRoomRepository;
    private final MessageRepository messageRepository;
    private final NotificationService notificationService;
    private final MessageService messageService;

    // 쪽지방(topic)에 발행되는 메시지 처리하는 리스너
    private final RedisMessageListenerContainer redisMessageListener;

    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    // redis
    private static final String Message_Rooms = "MESSAGE_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, MessageRoomDto> opsHashMessageRoom;

    // 쪽지방의 대화 메시지 발행을 위한 redis topic(쪽지방) 정보
    private Map<String, ChannelTopic> topics;       // 서버별로 쪽지방에 매치되는 topic 정보를 Map 에 넣어서, roomId 로 찾음

    // redis 의 Hash 데이터 다루기 위함
    @PostConstruct
    private void init() {
        opsHashMessageRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    // 쪽지방 생성
    public MessageResponseDto createRoom(MessageRequestDto messageRequestDto, User user) {
        Post post = postRepository.findById(messageRequestDto.getPostId()).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        User receiverId = userRepository.findByNickname(messageRequestDto.getReceiver());
        MessageRoom messageRoom = messageRoomRepository.findByUserIdAndReceiverIdAndPostId(user.getId(), receiverId.getId(), messageRequestDto.getPostId());

        // 처음 쪽지방 생성 또는 이미 생성된 쪽지방이 아닌 경우
        if ((messageRoom == null) || !(messageRoom.getPost().getId().equals(messageRequestDto.getPostId()))) {
            MessageRoomDto messageRoomDto = MessageRoomDto.create(user, receiverId);
            opsHashMessageRoom.put(Message_Rooms, messageRoomDto.getRoomId(), messageRoomDto);      // redis hash 에 쪽지방 저장해서, 서버간 채팅방 공유
            messageRoom = messageRoomRepository.save(new MessageRoom(messageRoomDto.getId(), messageRoomDto.getRoomName(), messageRoomDto.getSender(), messageRoomDto.getRoomId(), messageRoomDto.getReceiverId(), user, post));

            // 쪽지방 생성 알림
            notificationService.notifyCreateMessageRoom(messageRequestDto, messageRoom.getRoomId());

            return new MessageResponseDto(messageRoom);
            // 이미 생성된 쪽지방인 경우
        } else {
            return new MessageResponseDto(messageRoom.getRoomId());
        }
    }

    // 사용자 관련 쪽지방 전체 조회
    public List<MessageResponseDto> findAllRoomByUser(User user) {
        List<MessageRoom> messageRooms = messageRoomRepository.findByUserIdOrReceiverId(user.getId(), user.getId());

        List<MessageResponseDto> messageRoomDtos = new ArrayList<>();

        for (MessageRoom messageRoom : messageRooms) {
            // user 가 sender 인 경우
            if (user.getId().equals(messageRoom.getUser().getId())) {
                MessageResponseDto messageRoomDto = new MessageResponseDto(
                        messageRoom.getId(),
                        messageRoom.getRoomId(),
                        messageRoom.getCreatedAt());

                // roomName & emoticon
                MessageRoom messageRoomReceiver = messageRoomRepository.findByRoomId(messageRoom.getRoomId());
                User receiverUser = userRepository.findById(messageRoomReceiver.getReceiverId()).orElseThrow(
                        () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
                );
                messageRoomDto.setRoomName(receiverUser.getNickname());
                messageRoomDto.setEmoticon(receiverUser.getEmoticon());

                // 가장 최신 메시지 & 생성 시간 조회
                Message latestMessage = messageRepository.findTopByRoomIdOrderByCreatedAtDesc(messageRoom.getRoomId());
                if (latestMessage != null) {
                    messageRoomDto.setLatestMessageCreatedAt(latestMessage.getCreatedAt());
                    messageRoomDto.setLatestMessageContent(latestMessage.getMessage());
                }

                messageRoomDtos.add(messageRoomDto);
                // user 가 receiver 인 경우
            } else if (user.getId().equals(messageRoom.getReceiverId())) {
                MessageResponseDto messageRoomDto = new MessageResponseDto(
                        messageRoom.getId(),
                        messageRoom.getRoomId(),
                        messageRoom.getCreatedAt());

                // roomName & emoticon
                MessageRoom messageRoomSender = messageRoomRepository.findByRoomId(messageRoom.getRoomId());
                messageRoomDto.setRoomName(messageRoomSender.getUser().getNickname());
                messageRoomDto.setEmoticon(messageRoomSender.getUser().getEmoticon());

                // 가장 최신 메시지 & 생성 시간 조회
                Message latestMessage = messageRepository.findTopByRoomIdOrderByCreatedAtDesc(messageRoom.getRoomId());
                if (latestMessage != null) {
                    messageRoomDto.setLatestMessageCreatedAt(latestMessage.getCreatedAt());
                    messageRoomDto.setLatestMessageContent(latestMessage.getMessage());
                }

                messageRoomDtos.add(messageRoomDto);
            }
        }

        return messageRoomDtos;
    }

    // 사용자 관련 쪽지방 선택 조회
    public MessageRoomDto findRoom(String roomId, User user) {
        MessageRoom messageRoom = messageRoomRepository.findByRoomId(roomId);

        // 게시글 조회
        Post post = postRepository.findById(messageRoom.getPost().getId()).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        // 사용자 조회
        User receiver = userRepository.findById(post.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        messageRoom = messageRoomRepository.findByRoomId(roomId);
        if (messageRoom == null) {
            throw new IllegalArgumentException("쪽지방이 존재하지 않습니다.");
        }

        // user 가 sender 인 경우
        if (user.getId().equals(messageRoom.getUser().getId())) {
            MessageRoomDto messageRoomDto = new MessageRoomDto(
                    messageRoom.getId(),
                    messageRoom.getRoomId(),
                    user.getId(),
                    receiver.getNickname());        // roomName

            // emoticon
            MessageRoom messageRoomReceiver = messageRoomRepository.findByRoomId(roomId);
            User receiverUser = userRepository.findById(messageRoomReceiver.getReceiverId()).orElseThrow(
                    () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
            );
            messageRoomDto.setEmoticon(receiverUser.getEmoticon());

            messageRoomDto.setMessageRoomPostId(post.getId());
            messageRoomDto.setMessageRoomCategory(post.getCategory().getValue());
            messageRoomDto.setMessageRoomCountry(post.getCountry());
            messageRoomDto.setMessageRoomTitle(post.getTitle());

            return messageRoomDto;
            // user 가 receiver 인 경우
        } else if (user.getId().equals(messageRoom.getReceiverId())) {
            MessageRoomDto messageRoomDto = new MessageRoomDto(
                    messageRoom.getId(),
                    user.getId(),
                    messageRoom.getRoomId());

            // roomName & emoticon
            MessageRoom messageRoomSender = messageRoomRepository.findByRoomId(roomId);
            messageRoomDto.setRoomName(messageRoomSender.getUser().getNickname());
            messageRoomDto.setEmoticon(messageRoomSender.getUser().getEmoticon());

            messageRoomDto.setMessageRoomPostId(post.getId());
            messageRoomDto.setMessageRoomCategory(post.getCategory().getValue());
            messageRoomDto.setMessageRoomCountry(post.getCountry());
            messageRoomDto.setMessageRoomTitle(post.getTitle());

            return messageRoomDto;
        }

        return null;
    }

    // 쪽지방 삭제
    public MsgResponseDto deleteRoom(Long id, User user) {
        MessageRoom messageRoom = messageRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("쪽지방이 존재하지 않습니다.")
        );

        // 대화 기록 삭제 - redis
        messageService.deleteMessageList(messageRoom.getRoomId());
        
        // 쪽지방 삭제 - redis & DB
        if ((user.getId().equals(messageRoom.getUser().getId())) || (user.getId().equals(messageRoom.getReceiverId()))) {
            opsHashMessageRoom.delete(Message_Rooms, messageRoom.getRoomId());
            messageRoomRepository.delete(messageRoom);
        }

        return new MsgResponseDto("쪽지방을 삭제했습니다.", HttpStatus.OK.value());
    }

    // 쪽지방 입장
    public void enterMessageRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);        // pub/sub 통신을 위해 리스너를 설정. 대화가 가능해진다
            topics.put(roomId, topic);
        }
    }

    // redis 채널에서 쪽지방 조회
    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
package com.sparta.toogo.domain.notification.service;

import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.comment.repository.CommentRepository;
import com.sparta.toogo.domain.message.dto.MessageRequestDto;
import com.sparta.toogo.domain.message.repository.MessageRepository;
import com.sparta.toogo.domain.messageroom.dto.MsgResponseDto;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.notification.controller.NotificationController;
import com.sparta.toogo.domain.notification.dto.NotificationResponseDto;
import com.sparta.toogo.domain.notification.entity.Notification;
import com.sparta.toogo.domain.notification.repository.NotificationRepository;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final PostRepository postRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;
    private final UserRepository userRepository;

    // 메시지 알림
    public SseEmitter subscribe(Long userId) {
        // 현재 클라이언트를 위한 sseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            // 연결
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // user 의 pk 값을 key 값으로 해서 sseEmitter 를 저장
        NotificationController.sseEmitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onTimeout(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onError((e) -> NotificationController.sseEmitters.remove(userId));

        return sseEmitter;
    }

    // 쪽지방 생성 알림 - receiver 에게
    public void notifyCreateMessageRoom(MessageRequestDto messageRequestDto, String roomId) {
        User userReceiver = userRepository.findByNickname(messageRequestDto.getReceiver());

        MessageRoom messageRoom = messageRoomRepository.findByRoomId(roomId);

        User userSender = userRepository.findById(messageRoom.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        Long userId = userReceiver.getId();

        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
            try {
                Map<String, Object> eventData = new HashMap<>();
                eventData.put("message", "채팅방이 생성되었습니다.");
                eventData.put("sender", messageRoom.getSender());                    // 메시지 보낸자
                eventData.put("createdAt", messageRoom.getCreatedAt().toString());   // 메시지를 보낸 시간
                eventData.put("emoticon", userSender.getEmoticon());                 // 메시지 보낸자의 이모티콘

                boolean isNotificationRead = false;
                eventData.put("readStatus", isNotificationRead);

                sseEmitter.send(SseEmitter.event().name("addMessageRoom").data(eventData));

                // DB 저장
                Notification notification = new Notification();
                notification.setMessage("채팅방이 생성되었습니다.");
                notification.setSender(messageRoom.getSender());
                notification.setCreatedAt(messageRoom.getCreatedAt());
                notification.setRoomId(messageRoom.getRoomId());
                notification.setPost(messageRoom.getPost());         // post 필드 설정
                notification.setEmoticon(userSender.getEmoticon());
                notification.setReadStatus(isNotificationRead);
                notification.setUserId(userId);
                notificationRepository.save(notification);

            } catch (Exception e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }

    // 메시지 알림 - receiver 에게
//    public void notifyMessage(String roomId, Long receiverId, Long senderId) {
//        MessageRoom messageRoom = messageRoomRepository.findByRoomId(roomId);
//
//        Post post = postRepository.findById(messageRoom.getPost().getId()).orElseThrow(
//                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
//        );
//
//        Message receiveMessage = messageRepository.findFirstBySenderIdOrderByCreatedAtDesc(senderId).orElseThrow(
//                () -> new IllegalArgumentException("메시지를 찾을 수 없습니다.")
//        );
//
//        User userSender = userRepository.findById(senderId).orElseThrow(
//                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
//        );
//
//        Long userId = receiverId;
//
//        if (NotificationController.sseEmitters.containsKey(userId)) {
//            SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
//            try {
//                Map<String, Object> eventData = new HashMap<>();
//                eventData.put("message", "메시지가 왔습니다.");
//                eventData.put("sender", receiveMessage.getSender());                    // 메시지 보낸자
//                eventData.put("createdAt", receiveMessage.getCreatedAt().toString());   // 메시지를 보낸 시간
//                eventData.put("contents", receiveMessage.getMessage());                 // 메시지 내용
//                eventData.put("emoticon", userSender.getEmoticon());                    // 메시지 보낸자의 이모티콘
//
//                boolean isNotificationRead = false;
//                eventData.put("readStatus", isNotificationRead);
//
//                sseEmitter.send(SseEmitter.event().name("addMessage").data(eventData));
//
//                // DB 저장
//                Notification notification = new Notification();
//                notification.setMessage("메시지가 왔습니다.");
//                notification.setSender(receiveMessage.getSender());
//                notification.setCreatedAt(receiveMessage.getCreatedAt());
//                notification.setContents(receiveMessage.getMessage());
//                notification.setRoomId(messageRoom.getRoomId());
//                notification.setPost(post);         // post 필드 설정
//                notification.setEmoticon(userSender.getEmoticon());
//                notification.setReadStatus(isNotificationRead);
//                notification.setUserId(userId);
//                notificationRepository.save(notification);
//
//            } catch (Exception e) {
//                NotificationController.sseEmitters.remove(userId);
//            }
//        }
//    }

    // 댓글 알림 - 게시글 작성자 에게
    public void notifyComment(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        Comment receiveComment = commentRepository.findFirstByPostIdOrderByCreatedAtDesc(post.getId()).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        User userSender = userRepository.findById(receiveComment.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        Long userId = post.getUser().getId();

        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
            try {
                boolean isNotificationRead = false;

                if (!userSender.getId().equals(userId)) {
                    Map<String, Object> eventData = new HashMap<>();
                    eventData.put("message", "댓글이 달렸습니다.");
                    eventData.put("sender", receiveComment.getUser().getNickname());        // 댓글 작성자
                    eventData.put("createdAt", receiveComment.getCreatedAt().toString());   // 댓글이 달린 시간
                    eventData.put("contents", receiveComment.getComment());                 // 댓글 내용
                    eventData.put("emoticon", userSender.getEmoticon());                    // 댓글 작성자의 이모티콘

                    eventData.put("readStatus", isNotificationRead);

                    sseEmitter.send(SseEmitter.event().name("addComment").data(eventData));
                }

                // DB 저장
                Notification notification = new Notification();
                notification.setMessage("댓글이 달렸습니다.");
                notification.setSender(receiveComment.getUser().getNickname());
                notification.setCreatedAt(receiveComment.getCreatedAt());
                notification.setContents(receiveComment.getComment());
                notification.setPost(post);         // post 필드 설정
                notification.setEmoticon(userSender.getEmoticon());
                notification.setReadStatus(isNotificationRead);
                notification.setUserId(userId);
                notificationRepository.save(notification);

            } catch (IOException e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }

    // 알림 목록 조회
    public List<NotificationResponseDto> getNotificationList(User user) {
        List<Notification> notificationList = notificationRepository.findByUserId(user.getId());

        List<NotificationResponseDto> notificationResponseDtoList = new ArrayList<>();

        for (Notification notification : notificationList) {
            Post post = postRepository.findById(notification.getPost().getId()).orElseThrow(
                    () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
            );

            MessageRoom messageRoom = messageRoomRepository.findByPostIdAndRoomId(post.getId(), notification.getRoomId());

            // 댓글 알림일 경우
            if (notification.getRoomId() == null) {
                notificationResponseDtoList.add(new NotificationResponseDto(
                        notification.getId(),
                        notification.getSender(),
                        notification.getCreatedAt(),
                        notification.getContents(),
                        notification.getEmoticon(),
                        notification.getMessage(),
                        notification.getPost().getId(),
                        post.getCategory().getValue()));

                // 쪽지방 생성 알림일 경우
            } else if (notification.getMessage().equals("채팅방이 생성되었습니다.")) {
                notificationResponseDtoList.add(new NotificationResponseDto(
                        notification.getId(),
                        notification.getSender(),
                        notification.getCreatedAt(),
                        notification.getContents(),
                        notification.getEmoticon(),
                        notification.getMessage(),
                        messageRoom.getRoomId()));
            }
        }

        return notificationResponseDtoList;
    }

    // 알림 삭제
    public MsgResponseDto deleteNotification(Long id, User user) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("알림을 찾을 수 없습니다.")
        );

        if (!user.getId().equals(notification.getUserId())) {
            throw new IllegalArgumentException("알림 삭제 권한이 없습니다.");
        }

        notificationRepository.delete(notification);

        return new MsgResponseDto("알림이 삭제되었습니다.", HttpStatus.OK.value());
    }
}
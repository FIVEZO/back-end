package com.sparta.toogo.domain.notification.service;

import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.comment.repository.CommentRepository;
import com.sparta.toogo.domain.message.entity.Message;
import com.sparta.toogo.domain.message.repository.MessageRepository;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.notification.controller.NotificationController;
import com.sparta.toogo.domain.notification.entity.Notification;
import com.sparta.toogo.domain.notification.repository.NotificationRepository;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;
    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;
//    private static Map<Long, Integer> notificationCounts = new HashMap<>();     // 알림 개수 저장

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

    // 메시지 알림 - receiver 에게
    public void notifyMessage(String roomId, String receiver, String sender) {
        MessageRoom messageRoom = messageRoomRepository.findByRoomId(roomId);

        Post post = postRepository.findById(messageRoom.getPost().getId()).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        User user = userRepository.findByNickname(receiver);

        User userSender = userRepository.findByNickname(sender);

        Message receiveMessage = messageRepository.findFirstBySenderOrderByCreatedAtDesc(userSender.getNickname()).orElseThrow(
                () -> new IllegalArgumentException("메시지를 찾을 수 없습니다.")
        );

        Long userId = user.getId();

        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
            try {
//                Map<String, String> eventData = new HashMap<>();
                Map<String, Object> eventData = new HashMap<>();
                eventData.put("message", "메시지가 왔습니다.");
                eventData.put("sender", receiveMessage.getSender());                    // 메시지 보낸자
                eventData.put("createdAt", receiveMessage.getCreatedAt().toString());   // 메시지를 보낸 시간
                eventData.put("contents", receiveMessage.getMessage());                 // 메시지 내용

                boolean isNotificationRead = false;
                eventData.put("readStatus", isNotificationRead);

                sseEmitter.send(SseEmitter.event().name("addMessage").data(eventData));

                // DB 저장
                Notification notification = new Notification();
                notification.setSender(receiveMessage.getSender());
                notification.setCreatedAt(receiveMessage.getCreatedAt());
                notification.setContents(receiveMessage.getMessage());
                notification.setRoomId(messageRoom.getRoomId());
                notification.setPost(post);         // post 필드 설정
                notification.setReadStatus(isNotificationRead);
                notification.setUserId(userId);
                notificationRepository.save(notification);

//                // 알림 개수 증가
//                notificationCounts.put(userId, notificationCounts.getOrDefault(userId, 0) + 1);
//
//                // 현재 알림 개수 전송
//                sseEmitter.send(SseEmitter.event().name("notificationCount").data(notificationCounts.get(userId)));

            } catch (Exception e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }
    
    // 댓글 알림 - 게시글 작성자 에게
    public void notifyComment(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        Comment receiveComment = commentRepository.findFirstByPostIdOrderByCreatedAtDesc(post.getId()).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        Long userId = post.getUser().getId();

        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
            try {
//                Map<String, String> eventData = new HashMap<>();
                Map<String, Object> eventData = new HashMap<>();
                eventData.put("message", "댓글이 달렸습니다.");
                eventData.put("sender", receiveComment.getUser().getNickname());        // 댓글 작성자
                eventData.put("createdAt", receiveComment.getCreatedAt().toString());   // 댓글이 달린 시간
                eventData.put("contents", receiveComment.getComment());                 // 댓글 내용

                boolean isNotificationRead = false;
                eventData.put("readStatus", isNotificationRead);

                sseEmitter.send(SseEmitter.event().name("addComment").data(eventData));

                // DB 저장
                Notification notification = new Notification();
                notification.setSender(receiveComment.getUser().getNickname());
                notification.setCreatedAt(receiveComment.getCreatedAt());
                notification.setContents(receiveComment.getComment());
                notification.setPost(post);         // post 필드 설정
//                notification.setReadStatus(isNotificationRead);
                notification.setReadStatus(isNotificationRead);
                notification.setUserId(userId);
                notificationRepository.save(notification);

//                markNotificationAsRead(notification.getId());       // 알림 읽기 상태 변경

            } catch (IOException e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }

    // 알림 읽기 상태 변경
//    @Transactional
//    public void getReadStatus(User user) {
//        List<Notification> notifications = notificationRepository.findByUserId(user.getId());
//
//        for (Notification notification : notifications) {
//            if (!notification.isReadStatus()) {
//                notification.setReadStatus(true);
//                notificationRepository.save(notification);
//            }
//        }
//    }

    // 알림 삭제
//    public MsgResponseDto deleteNotification(Long id) throws IOException {
//        Notification notification = notificationRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("알림을 찾을 수 없습니다.")
//        );
//
//        Long userId = notification.getPost().getUser().getId();
//
//        notificationRepository.delete(notification);
//
//        // 알림 개수 감소
//        if (notificationCounts.containsKey(userId)) {
//            int currentCount = notificationCounts.get(userId);
//            if (currentCount > 0) {
//                notificationCounts.put(userId, currentCount - 1);
//            }
//        }
//
//        // 현재 알림 개수 전송
//        SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
//        sseEmitter.send(SseEmitter.event().name("notificationCount").data(notificationCounts.get(userId)));
//
//        return new MsgResponseDto("알림이 삭제되었습니다.", HttpStatus.OK.value());
//    }
}
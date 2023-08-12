package com.sparta.toogo.domain.notification.service;

import com.sparta.toogo.domain.notification.controller.NotificationController;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final PostRepository postRepository;
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

    // 메시지 알림 - receiver 에게
    public void notifyMessage(String receiver) {
        User user = userRepository.findByNickname(receiver);

        Long userId = user.getId();

        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = NotificationController.sseEmitters.get(userId);
            try {
                sseEmitterReceiver.send(SseEmitter.event().name("addMessage").data("메시지가 왔습니다."));
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

        Long userId = post.getUser().getId();

        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
            try {
                sseEmitter.send(SseEmitter.event().name("addComment").data("댓글이 달렸습니다."));
            } catch (Exception e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }
}
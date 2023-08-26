package com.sparta.toogo.domain.notification.controller;

import com.sparta.toogo.domain.notification.service.NotificationService;
import com.sparta.toogo.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    // 메시지 알림
    @GetMapping("/api/notification/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        SseEmitter sseEmitter = notificationService.subscribe(userId);

        return sseEmitter;
    }

    // 알림 읽음 --> 알림 목록 창 누르면, post 요청이 들어와서 false 를 모두 true 로 변경??
//    @PostMapping("/api/notification/readStatus")
//    public void getReadStatus(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        notificationService.getReadStatus(userDetails.getUser());
//    }

//    // 알림 삭제
//    @DeleteMapping("/api/notification/delete/{id}")
//    public MsgResponseDto deleteNotification(@PathVariable Long id) throws IOException {
//        return notificationService.deleteNotification(id);
//    }
}
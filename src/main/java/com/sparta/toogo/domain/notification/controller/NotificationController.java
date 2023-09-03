package com.sparta.toogo.domain.notification.controller;

import com.sparta.toogo.domain.messageroom.dto.MsgResponseDto;
import com.sparta.toogo.domain.notification.dto.NotificationResponseDto;
import com.sparta.toogo.domain.notification.service.NotificationService;
import com.sparta.toogo.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
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

    // 알림 목록 조회
    @GetMapping("/api/notifications")
    private ResponseEntity<List<NotificationResponseDto>> getNotificationList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(notificationService.getNotificationList(userDetails.getUser()));
    }

    // 알림 삭제
    @DeleteMapping("/api/notification/{id}")
    public MsgResponseDto deleteNotification(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.deleteNotification(id, userDetails.getUser());
    }
}
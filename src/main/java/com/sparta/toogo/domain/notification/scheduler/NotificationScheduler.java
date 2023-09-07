package com.sparta.toogo.domain.notification.scheduler;

import com.sparta.toogo.domain.notification.entity.Notification;
import com.sparta.toogo.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j(topic = "Scheduler")
@Service
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationRepository notificationRepository;

    // 6시간 마다 생성일 기준 2일 지난 알림 삭제
    @Scheduled(cron = "0 0 */6 * * *")
    public void deleteNotification() {
        LocalDateTime twoDaysAgo = LocalDateTime.now().minus(2, ChronoUnit.DAYS);

        List<Notification> notifications = notificationRepository.findByCreatedAtBefore(twoDaysAgo);

        notificationRepository.deleteAll(notifications);

        log.info(notifications.size() + "개 알림 삭제 완료");
    }
}
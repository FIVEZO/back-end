package com.sparta.toogo.domain.notification.scheduler;

import com.sparta.toogo.domain.notification.entity.Notification;
import com.sparta.toogo.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j(topic = "Scheduler")
@Service
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationRepository notificationRepository;

    // 알림 삭제 (2일 마다)
    @Scheduled(cron = "0 0 0 */2 * *")
//    @Scheduled(cron = "0 */1 * * * *")
    public void deleteNotification() {
        List<Notification> notifications = notificationRepository.findAll();
        notificationRepository.deleteAll(notifications);

        log.info("알림 삭제 완료");
    }
}
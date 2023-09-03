package com.sparta.toogo.domain.notification.repository;

import com.sparta.toogo.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findById(Long id);

    List<Notification> findByUserId(Long id);


    List<Notification> findByPostId(Long postId);

    List<Notification> findByCreatedAtBefore(LocalDateTime twoDaysAgo);
}
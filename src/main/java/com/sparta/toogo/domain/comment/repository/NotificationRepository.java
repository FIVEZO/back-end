package com.sparta.toogo.domain.comment.repository;

import com.sparta.toogo.domain.comment.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

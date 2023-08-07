package com.sparta.toogo.domain.message.repository;

import com.sparta.toogo.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

package com.sparta.toogo.domain.message.repository;

import com.sparta.toogo.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomId(String roomId);

    List<Message> findAllById(Long id);
//    Optional<Message> findByRoomId(String roomId);
}

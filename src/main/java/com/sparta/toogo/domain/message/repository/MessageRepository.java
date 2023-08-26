package com.sparta.toogo.domain.message.repository;

import com.sparta.toogo.domain.message.dto.MessageDto;
import com.sparta.toogo.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findByRoomId(String roomId);
    MessageDto findByRoomId(String roomId);


    List<Message> findAllById(Long id);

    List<Message> findTop100ByRoomIdOrderByCreatedAtAsc(String roomId);

    Message findTopByRoomIdOrderByCreatedAtDesc(String roomId);

    Optional<Message> findFirstBySenderOrderByCreatedAtDesc(String nickname);
//    Optional<Message> findByRoomId(String roomId);
}

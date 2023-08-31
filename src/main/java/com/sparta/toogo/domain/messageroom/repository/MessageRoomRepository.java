package com.sparta.toogo.domain.messageroom.repository;

import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    MessageRoom findByRoomId(String roomId);
    List<MessageRoom> findByUserIdOrReceiverId(Long id, Long id1);
    MessageRoom findByUserIdAndReceiverIdAndPostId(Long id, Long id1, Long postId);
    List<MessageRoom> findByPostId(Long postId);
    MessageRoom findByPostIdAndUserIdOrPostIdAndReceiverId(Long id, Long id1, Long id2, Long id3);
}

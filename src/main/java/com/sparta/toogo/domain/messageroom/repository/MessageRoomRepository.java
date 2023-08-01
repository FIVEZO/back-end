package com.sparta.toogo.domain.messageroom.repository;

import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    MessageRoom findByIdAndUser(Long id, User user);
}

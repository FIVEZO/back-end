package com.sparta.toogo.domain.messageroom.repository;

import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    MessageRoom findByIdAndUserOrIdAndReceiver(Long id, User user, Long id1, String nickname);
    MessageRoom findByRoomIdAndUserOrRoomIdAndReceiver(String roomId, User user, String roomId1, String nickname);
    MessageRoom findByRoomId(String roomId);
    MessageRoom findByReceiver(String nickname);
    List<MessageRoom> findByUserIdOrReceiverUserId(Long id, Long id1);
    MessageRoom findByUserIdAndReceiverUserIdAndPostId(Long id, Long id1, Long postId);
}

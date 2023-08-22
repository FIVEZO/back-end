package com.sparta.toogo.domain.messageroom.repository;

import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    List<MessageRoom> findByUserOrReceiver(User user, String receiver);

    MessageRoom findByIdAndUserOrIdAndReceiver(Long id, User user, Long id1, String nickname);
//    MessageRoom findByIdAndRoomId(Long id);

//    Optional<MessageRoom> findAllById(Long id);

//    MessageRoom findBySenderOrReceiver(String nickname, String receiver);
    MessageRoom findBySenderAndReceiver(String nickname, String receiver);

    MessageRoom findByRoomIdAndUserOrRoomIdAndReceiver(String roomId, User user, String roomId1, String nickname);

    MessageRoom findByRoomId(String roomId);
    MessageRoom findByReceiver(String nickname);

//    MessageRoom findBySenderAndReceiver();
}

package com.sparta.toogo.domain.messageroom.service;

import com.sparta.toogo.domain.messageroom.dto.MsgResponseDto;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.messageroom.repository.MessageRoomRepository;
import com.sparta.toogo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageRoomService {
    private final MessageRoomRepository messageRoomRepository;

    public MsgResponseDto deleteRoom(Long id, User user) {
        MessageRoom messageRoom = messageRoomRepository.findByIdAndUser(id, user);

        messageRoomRepository.delete(messageRoom);

        return new MsgResponseDto("채팅방을 삭제했습니다.", HttpStatus.OK.value());
    }
}

package com.sparta.toogo.domain.message.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.toogo.domain.message.dto.MessageDto;
import com.sparta.toogo.domain.message.service.MessageService;
import com.sparta.toogo.domain.messageroom.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final MessageService messageService;
    private final MessageRoomService messageRoomService;
    private Set<WebSocketSession> sessions = new HashSet<>();

    // 클라이언트 연결 이후 실행
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        // 전달받은 메시지
        String payload = textMessage.getPayload();      // 메세지를 json 형식으로 웹소켓을 통해 서버 보낸다

        // Json 객체 → Java 객체
            // Handler 는 이를 받아, ObjectMapper 를 통해서 해당 json 데이터를 MessageDto.class 에 맞게 파싱하여 MessageDto 객체로 변환
        MessageDto messageDto = objectMapper.readValue(payload, MessageDto.class);

        // 이 json 데이터에 들어있는 roomId 를 이용해서, 해당 채팅방을 찾아 handlerAction() 이라는 메서드를 실행
        messageRoomService.findRoomById(messageDto.getRoomId());
        handlerActions(session, messageDto, messageService);
    }

    // 해당 참여자가 채팅방에 접속 상태인지, 이미 참여해 있는지에 따라 메시지 전송 방식 차별
    public void handlerActions(WebSocketSession session, MessageDto messageDto, MessageService messageService) {
        sessions.add(session);
        sendMessage(messageDto, messageService);
    }

    // 메세지 전송
    private <T> void sendMessage(T message, MessageService messageService) {
        sessions.parallelStream()
                .forEach(session -> messageService.sendMessage(session, message));
    }

    // session 삭제
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) {
        sessions.remove(webSocketSession);
    }
}
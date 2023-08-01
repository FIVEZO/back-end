package com.sparta.toogo.domain.message.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket        // webSocket 활성화
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    // 웹소켓 연결
    @Override
    public void registerWebSocketHandlers (WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "ws/chat")        // ws/chat : 웹소켓을 사용하기 위한 주소
                .setAllowedOrigins("*");
    }
}

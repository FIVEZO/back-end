package com.sparta.toogo.domain.message.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
//@EnableWebSocket        // webSocket 활성화
@EnableWebSocketMessageBroker       // stomp 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // stomp websocket 연결
    @Override
    public void  registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")      // ws://localhost8080/ws-stomp
                .setAllowedOriginPatterns("*")
                .withSockJS();      // 클라이언트와 연결
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("pub");
    }
}
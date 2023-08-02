package com.sparta.toogo.domain.message.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker       // stomp 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // stomp websocket 연결
    @Override
    public void  registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");        // 메시지 구독 요청
        registry.setApplicationDestinationPrefixes("/pub");        // 메시지 발행 요청
    }
}
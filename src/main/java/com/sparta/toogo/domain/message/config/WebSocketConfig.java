package com.sparta.toogo.domain.message.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocket        // 추가 : websocket 연결
@EnableWebSocketMessageBroker       // stomp 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // stomp websocket 연결
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")       // 해당 설정이 있으면 postman 으로 웹소켓 연결 불가능
                .setAllowedOrigins("*");
//                .withSockJS();
    }

    // Stomp 사용을 위한 Message Broker 설정.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");        // 메시지 구독 요청 : 메시지 수신. Broker 가 해당 경로를 가로챈다
        registry.setApplicationDestinationPrefixes("/pub");        // 메시지 발행 요청 : 메시지 전송. Broker 에게로 전달된다
    }
}
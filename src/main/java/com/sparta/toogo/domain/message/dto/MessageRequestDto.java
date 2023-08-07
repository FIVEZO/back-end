package com.sparta.toogo.domain.message.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRequestDto {
    private String receiver;    // 메세지 수신자
}

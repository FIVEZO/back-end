package com.sparta.toogo.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {
    private String receiver;    // 메세지 수신자
    private Long postId;        // 게시글 id
}

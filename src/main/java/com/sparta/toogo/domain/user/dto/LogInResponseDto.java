package com.sparta.toogo.domain.user.dto;

import lombok.Getter;

@Getter
public class LogInResponseDto {
    private String email;
    private String nickname;
    private String emoticon;

    public LogInResponseDto(String email, String nickname, String emoticon) {
        this.email = email;
        this.nickname = nickname;
        this.emoticon = emoticon;
    }
}
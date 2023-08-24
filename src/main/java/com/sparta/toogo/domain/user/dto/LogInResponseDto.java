package com.sparta.toogo.domain.user.dto;

import com.sparta.toogo.domain.user.entity.User;
import lombok.Getter;

@Getter
public class LogInResponseDto {
    private String email;
    private String nickname;
    private String introduction;
    private String emotion;

    public LogInResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.introduction = user.getMyPage().getIntroduction();
        this.emotion = user.getEmotion();
    }
}
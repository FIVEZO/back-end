package com.sparta.toogo.domain.mypage.dto;

import lombok.Getter;

@Getter
public class MyPagePatchResponseDto {
    private String nickname;
    private String introduction;
    private String emotion;

    public MyPagePatchResponseDto(String nickname, String introduction, String emotion) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.emotion = emotion;
    }
}

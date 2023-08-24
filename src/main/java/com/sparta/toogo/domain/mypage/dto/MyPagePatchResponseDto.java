package com.sparta.toogo.domain.mypage.dto;

import lombok.Getter;

@Getter
public class MyPagePatchResponseDto {
    private String newNickname;
    private String newIntroduction;
    private String newEmoticon;

    public MyPagePatchResponseDto(String nickname, String introduction, String emoticon) {
        this.newNickname = nickname;
        this.newIntroduction = introduction;
        this.newEmoticon = emoticon;
    }
}
package com.sparta.toogo.domain.kakao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {

    private Long id;
    private String email;
    private String nickname;

    public KakaoUserInfoDto(Long kakaoId, String email, String nickname) {
        this.id = kakaoId;
        this.email = email;
        this.nickname = nickname;
    }

}
package com.sparta.toogo.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageRequestDto {
    private String nickname;
    private String introduction;
    private String password;
    private String newPassword;
    private String emotion;
}
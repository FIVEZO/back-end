package com.sparta.toogo.domain.mypage.dto;

import com.sparta.toogo.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageResponseDto {
    private String nickname;
    private String profileImg;
    private String msg;
    private int statusCode;

    public MyPageResponseDto(User user) {
        this.nickname = user.getNickname();
    }


//    public static MyPageResponseDto success(String msg, String nickname, String profileImg) {
//        return new MyPageResponseDto(nickname, profileImg, msg, HttpStatus.OK.value());
//    }
}

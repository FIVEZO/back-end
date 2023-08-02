package com.sparta.toogo.domain.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageDto {

    //    @NotEmpty(message = "닉네임은 필수입니다.")
    private String nickname;

    private String profileImg;

    private String email;

    private String loginId;


    public MyPageDto(String nickname, String profileImg){
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

//    public MyPageDto(User user){
//        this.nickname = user.getNickname();
//        this.profileImg = user.getProfileImg();
//
//    }
}

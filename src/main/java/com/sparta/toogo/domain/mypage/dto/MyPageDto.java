package com.sparta.toogo.domain.mypage.dto;

import com.sparta.toogo.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageDto {

    private Long myScrapCount;
    private String nickname;

    public MyPageDto(Long myScrapCount, User user) {
        this.myScrapCount = myScrapCount;
        this.nickname = user.getNickname();
    }

//    public MyPageDto(User user){
//        this.nickname = user.getNickname();
//        this.profileImg = user.getProfileImg();
//
//    }
}

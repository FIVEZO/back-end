package com.sparta.toogo.domain.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageDto {

    private Long myScrapCount;

    public MyPageDto(Long myScrapCount) {
        this.myScrapCount = myScrapCount;
    }

//    public MyPageDto(User user){
//        this.nickname = user.getNickname();
//        this.profileImg = user.getProfileImg();
//
//    }
}

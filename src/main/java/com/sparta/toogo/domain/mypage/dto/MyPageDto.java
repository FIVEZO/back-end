package com.sparta.toogo.domain.mypage.dto;

import com.sparta.toogo.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageDto {

    private Long myScrapCount;
    private String nickname;
//    private String profileImg;

    public MyPageDto(Long myScrapCount, User user) {
        this.myScrapCount = myScrapCount;
        this.nickname = user.getNickname();
 //       this.profileImg = user.getProfileImg();
    }

//    public MyPageDto(User user){
//        this.nickname = user.getNickname();
//        this.profileImg = user.getProfileImg();
//
//    }
}

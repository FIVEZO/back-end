package com.sparta.toogo.domain.mypage.dto;

import lombok.Getter;

@Getter
public class MyPageCountDto {
    private Long myPost;
    private Long myScrap;

    public MyPageCountDto(Long myPost, Long myScrap) {
        this.myPost = myPost;
        this.myScrap = myScrap;
    }
}

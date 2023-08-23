package com.sparta.toogo.domain.mypage.dto;

import com.sparta.toogo.global.enums.SuccessCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MyPageResponseDto {
    private HttpStatus status;
    private String msg;

    public MyPageResponseDto(SuccessCode successCode) {
        this.status = successCode.getHttpStatus();
        this.msg = successCode.getDetail();
    }

//    public static MyPageResponseDto success(String msg, String nickname, String profileImg) {
//        return new MyPageResponseDto(nickname, profileImg, msg, HttpStatus.OK.value());
//    }
}
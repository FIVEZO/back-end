package com.sparta.toogo.domain.mypage.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class MyPageException extends GlobalException {
    public MyPageException(ErrorCode errorCode) {
        super(errorCode);
    }
}
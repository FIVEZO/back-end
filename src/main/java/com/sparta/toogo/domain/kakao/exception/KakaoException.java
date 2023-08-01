package com.sparta.toogo.domain.kakao.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class KakaoException extends GlobalException {
    public KakaoException(ErrorCode errorCode) {
        super(errorCode);
    }
}
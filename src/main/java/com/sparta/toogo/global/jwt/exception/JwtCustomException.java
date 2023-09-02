package com.sparta.toogo.global.jwt.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class JwtCustomException extends GlobalException {
    public JwtCustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
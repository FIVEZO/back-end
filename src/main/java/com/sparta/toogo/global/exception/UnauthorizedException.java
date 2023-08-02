package com.sparta.toogo.global.exception;

import com.sparta.toogo.global.enums.ErrorCode;

public class UnauthorizedException extends GlobalException{
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

//    public UnauthorizedException(String message) {
//        super(message);
//    }
//
//    public UnauthorizedException(String message, Throwable cause) {
//        super(message, cause);
//    }
}

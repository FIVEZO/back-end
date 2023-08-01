package com.sparta.toogo.global.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
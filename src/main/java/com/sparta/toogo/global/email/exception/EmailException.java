package com.sparta.toogo.global.email.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class EmailException extends GlobalException {
    public EmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
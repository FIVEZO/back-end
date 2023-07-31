package com.sparta.toogo.domain.user.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class UserException extends GlobalException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
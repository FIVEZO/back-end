package com.sparta.toogo.domain.message.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class MessageException extends GlobalException {
    public MessageException(ErrorCode errorCode) {
        super(errorCode);
    }
}
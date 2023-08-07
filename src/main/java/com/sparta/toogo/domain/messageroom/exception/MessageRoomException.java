package com.sparta.toogo.domain.messageroom.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class MessageRoomException extends GlobalException {
    public MessageRoomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
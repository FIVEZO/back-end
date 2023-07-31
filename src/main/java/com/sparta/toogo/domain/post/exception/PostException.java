package com.sparta.toogo.domain.post.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class PostException extends GlobalException {
    public PostException(ErrorCode errorCode) {
        super(errorCode);
    }
}
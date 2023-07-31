package com.sparta.toogo.domain.comment.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class CommentException extends GlobalException {
    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
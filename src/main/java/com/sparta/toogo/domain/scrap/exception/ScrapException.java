package com.sparta.toogo.domain.scrap.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class ScrapException extends GlobalException {
    public ScrapException(ErrorCode errorCode) {
        super(errorCode);
    }
}
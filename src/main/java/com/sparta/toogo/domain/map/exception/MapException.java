package com.sparta.toogo.domain.map.exception;

import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.GlobalException;

public class MapException extends GlobalException {
    public MapException(ErrorCode errorCode) {
        super(errorCode);
    }
}
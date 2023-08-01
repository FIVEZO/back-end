package com.sparta.toogo.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    WRONG_POST(HttpStatus.NOT_FOUND.value(),"해당 글을 찾을 수 없습니다."),
    NO_COMMENT(HttpStatus.NOT_FOUND.value(), "해당 댓글을 찾을 수 없습니다."),

    NOTFOUND_POST(HttpStatus.BAD_REQUEST.value(),"게시글을 찾을 수 없습니다."),
    NOAUTH_UPDATE(HttpStatus.BAD_REQUEST.value(),"작성자 본인만 수정 가능합니다.");

    private final int statusCode;
    private final String message;
}

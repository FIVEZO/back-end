package com.sparta.toogo.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 이 응답은 잘못된 문법으로 인해 서버가 요청을 이해할 수 없다는 의미입니다. */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다."),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다."),
    INCORRECT_CODE(BAD_REQUEST, "잘못된 인증번호입니다."),
    CODE_VERIFICATION_COMPLETED(BAD_REQUEST, "올바른 인증 코드를 입력해 주세요."),

    /* 401 UNAUTHORIZED : 인증되지 않았다는 의미입니다. */
    INVALID_ADMIN_NUMBER(UNAUTHORIZED, "관리자 번호가 유효하지 않습니다."),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    INCORRECT_PASSWORD(UNAUTHORIZED, "잘못된 비밀번호입니다."),

    /* 403 FORBIDDEN : 클라이언트가 콘텐츠에 접근할 권리를 가지고 있지 않다는 의미입니다.*/
    NO_AUTHORITY_TO_DATA(FORBIDDEN, "작성자만 수정, 삭제할 수 있습니다."),

    /* 404 NOT_FOUND : 서버는 요청 받은 리소스를 찾을 수 없다는 의미입니다. */
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
    ID_NOT_FOUND(NOT_FOUND, "ID 를 찾을 수 없습니다."),
    NOT_FOUND_CLIENT(NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_DATA(NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다."),
    NOT_FOUND_EMAIL(NOT_FOUND, "다시 시도해 주세요."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "중복된 이메일 또는 닉네임입니다."),
    DUPLICATE_NICKNAME(CONFLICT, "중복된 닉네임입니다."),
    DUPLICATE_EMAIL(CONFLICT, "중복된 이메일입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    ErrorCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}

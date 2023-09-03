package com.sparta.toogo.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 이 응답은 잘못된 문법으로 인해 서버가 요청을 이해할 수 없다는 의미입니다. */
    INCORRECT_CODE(BAD_REQUEST, "잘못된 인증번호입니다."),
    EMAIL_REQUIRED(BAD_REQUEST, "이메일을 입력해 주세요."),
    EMAIL_CODE_INCOMPLETE(BAD_REQUEST, "올바른 이메일 또는 인증 코드를 입력해 주세요."),
    CODE_SEND_FAILED(BAD_REQUEST, "인증 코드 전송이 실패하였습니다."),
    NEW_PASSWORD_SEND_FAILED(BAD_REQUEST, "새 비밀번호 전송이 실패하였습니다."),
    PASSWORD_REQUIRED(BAD_REQUEST, "비밀번호를 입력해주세요."),
    PASSWORD_MISMATCH(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_PASSWORD_FORMAT(BAD_REQUEST, "비밀번호는 대소문자 영문과 숫자 조합으로 8자 이상 15자 이하로 입력해주세요."),
    NICKNAME_LENGTH_INVALID(BAD_REQUEST, "닉네임은 2자 이상 15자 이하로 입력해주세요."),
    NICKNAME_FORMAT_INVALID(BAD_REQUEST, "닉네임은 문자와 숫자만 포함되어야 합니다."),
    TOO_LONG_INTRODUCTION(BAD_REQUEST, "자기소개는 70자 이하로 입력해주세요."),
    EXCEPTED_KAKAO_USER(BAD_REQUEST, "카카오 회원은 비밀번호 변경이 불가능합니다."),

    /* 401 UNAUTHORIZED : 인증되지 않았다는 의미입니다. */
    INVALID_ADMIN_NUMBER(UNAUTHORIZED, "관리자 번호가 유효하지 않습니다."),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    INCORRECT_PASSWORD(UNAUTHORIZED, "잘못된 비밀번호입니다."),

    /* 403 FORBIDDEN : 클라이언트가 콘텐츠에 접근할 권리를 가지고 있지 않다는 의미입니다.*/
    NO_AUTHORITY_TO_DATA(FORBIDDEN, "작성자만 수정, 삭제할 수 있습니다."),
    INVALID_TOKEN(FORBIDDEN, "토큰이 유효하지 않습니다."),
    MISMATCH_TOKEN(FORBIDDEN, "토큰의 유저 정보가 일치하지 않습니다."),
    TOKEN_MISSING(FORBIDDEN, "엑세스 토큰 또는 리프레시 토큰이 존재하지 않습니다."),
    REFRESH_TOKEN_FORGERY(FORBIDDEN, "리프레시 토큰이 위조되었습니다."),

    /* 404 NOT_FOUND : 서버는 요청 받은 리소스를 찾을 수 없다는 의미입니다. */
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
    EMAIL_NOT_FOUND(NOT_FOUND, "해당 이메일을 찾을 수 없습니다."),
    ID_NOT_FOUND(NOT_FOUND, "ID를 찾을 수 없습니다."),
    NOT_FOUND_CLIENT(NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_DATA(NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다."),
    NOT_FOUND_EMAIL(NOT_FOUND, "다시 시도해 주세요."),
    EMPTY_TITLE_OR_CONTENTS(NOT_FOUND, "제목이나 내용을 입력해주세요."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "중복된 이메일 또는 닉네임입니다."),
    DUPLICATE_NICKNAME(CONFLICT, "중복된 닉네임입니다."),
    DUPLICATE_EMAIL(CONFLICT, "중복된 이메일입니다."),

    REGENERATED_TOKEN(I_AM_A_TEAPOT, "토큰 재발급 완료")
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    ErrorCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}

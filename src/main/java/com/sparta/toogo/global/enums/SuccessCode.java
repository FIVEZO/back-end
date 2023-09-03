package com.sparta.toogo.global.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
public enum SuccessCode {
    /* 200 OK : 요청이 성공적으로 완료되었다는 의미입니다. */
    USER_LOGIN_SUCCESS(OK, "로그인 성공"),
    POST_UPDATE_SUCCESS(OK, "게시글 수정이 완료되었습니다."),
    POST_DELETE_SUCCESS(OK, "게시글 삭제가 완료되었습니다."),
    COMMENT_UPDATE_SUCCESS(OK, "댓글 수정이 완료되었습니다."),
    COMMENT_DELETE_SUCCESS(OK, "댓글 삭제가 완료되었습니다."),
    LIKE_CANCEL(OK, "좋아요 취소"),
    DISLIKE_CANCEL(OK, "싫어요 취소"),
    LOGOUT_SUCCESS(OK, "로그아웃 성공"),
    EMAIL_VERIFICATION_SENT(OK, "이메일 인증 코드가 전송되었습니다."),
    NEW_PASSWORD_SENT(OK, "새 비밀번호가 전송되었습니다."),
    PASSWORD_CHANGE_SUCCESS(OK, "비밀번호 변경이 완료되었습니다."),

    /* 201 CREATED : 요청이 성공적이었으며 그 결과로 새로운 리소스가 생성 되었다는 의미입니다. */
    USER_SIGNUP_SUCCESS(CREATED, "회원가입 성공"),
    POST_CREATE_SUCCESS(CREATED, "게시글이 등록되었습니다."),
    COMMENT_CREATE_SUCCESS(CREATED, "댓글이 등록되었습니다."),
    LIKE_SUCCESS(CREATED, "좋아요 성공"),
    DISLIKE_SUCCESS(CREATED, "싫어요 성공"),
    REGENERATED_TOKEN(CREATED, "토큰 재발급 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    SuccessCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
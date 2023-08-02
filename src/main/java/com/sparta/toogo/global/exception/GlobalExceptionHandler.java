package com.sparta.toogo.global.exception;

import com.sparta.toogo.domain.comment.exception.CommentException;
import com.sparta.toogo.domain.kakao.exception.KakaoException;
import com.sparta.toogo.domain.map.exception.MapException;
import com.sparta.toogo.domain.message.exception.MessageException;
import com.sparta.toogo.domain.messageroom.exception.MessageRoomException;
import com.sparta.toogo.domain.mypage.exception.MyPageException;
import com.sparta.toogo.domain.post.exception.PostException;
import com.sparta.toogo.domain.scrap.exception.ScrapException;
import com.sparta.toogo.domain.user.exception.UserException;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommentException.class)
    public ApiResponse<?> handleUserException(CommentException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(MapException.class)
    public ApiResponse<?> handleUserException(MapException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(MessageException.class)
    public ApiResponse<?> handleUserException(MessageException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(MessageRoomException.class)
    public ApiResponse<?> handleUserException(MessageRoomException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(MyPageException.class)
    public ApiResponse<?> handleUserException(MyPageException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(PostException.class)
    public ApiResponse<?> handleUserException(PostException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(ScrapException.class)
    public ApiResponse<?> handleUserException(ScrapException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(UserException.class)
    public ApiResponse<?> handleUserException(UserException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(KakaoException.class)
    public ApiResponse<?> handleUserException(KakaoException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ApiResponse<?> handleUserException(UnauthorizedException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> validationExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(), error.getDefaultMessage()
                ));
        return ResponseUtil.error(HttpStatus.BAD_REQUEST, errors);
    }
}

package com.sparta.toogo.global.exception;

import com.sparta.toogo.domain.comment.exception.CommentException;
import com.sparta.toogo.domain.map.exception.MapException;
import com.sparta.toogo.domain.message.exception.MessageException;
import com.sparta.toogo.domain.messageroom.exception.MessageRoomException;
import com.sparta.toogo.domain.mypage.exception.MyPageException;
import com.sparta.toogo.domain.post.exception.PostException;
import com.sparta.toogo.domain.scrap.exception.ScrapException;
import com.sparta.toogo.domain.user.exception.UserException;
import com.sparta.toogo.global.email.exception.EmailException;
import com.sparta.toogo.global.jwt.exception.JwtCustomException;
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
    public ApiResponse<?> handleCommentException(CommentException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(MapException.class)
    public ApiResponse<?> handleMapException(MapException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(MessageException.class)
    public ApiResponse<?> handleMessageException(MessageException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(MessageRoomException.class)
    public ApiResponse<?> handleMessageRoomException(MessageRoomException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(MyPageException.class)
    public ApiResponse<?> handleMyPageException(MyPageException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(PostException.class)
    public ApiResponse<?> handlePostException(PostException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(ScrapException.class)
    public ApiResponse<?> handleScrapException(ScrapException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(UserException.class)
    public ApiResponse<?> handleUserException(UserException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ApiResponse<?> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(EmailException.class)
    public ApiResponse<?> handleEmailException(EmailException e) {
        return ResponseUtil.error(e.getErrorCode());
    }

    @ExceptionHandler(JwtCustomException.class)
    public ApiResponse<?> handleJwtException(JwtCustomException e) {
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

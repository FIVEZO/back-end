package com.sparta.toogo.domain.user.dto;

import com.sparta.toogo.global.enums.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String msg;
    private HttpStatus status;

    public UserResponseDto(SuccessCode successCode) {
        this.msg = successCode.getDetail();
        this.status = successCode.getHttpStatus();
    }
}
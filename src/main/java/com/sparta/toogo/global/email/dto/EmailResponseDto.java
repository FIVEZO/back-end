package com.sparta.toogo.global.email.dto;

import com.sparta.toogo.global.enums.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class EmailResponseDto {
    private String msg;
    private HttpStatus status;

    public EmailResponseDto(SuccessCode successCode) {
        this.msg = successCode.getDetail();
        this.status = successCode.getHttpStatus();
    }
}
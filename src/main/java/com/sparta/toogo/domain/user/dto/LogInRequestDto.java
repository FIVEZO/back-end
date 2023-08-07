package com.sparta.toogo.domain.user.dto;

import lombok.Getter;

@Getter
public class LogInRequestDto {
    private String email;
    private String password;
}

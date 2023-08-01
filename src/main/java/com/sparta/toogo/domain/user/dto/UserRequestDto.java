package com.sparta.toogo.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @Email(message = "이메일 양식이 아닙니다")
    @NotBlank(message = "Email 공백 불가")
    private String email;

    @NotBlank(message = "password 공백 불가")
    private String password;

    @NotBlank(message = "닉네임 공백 불가")
    private String nickname;

    private boolean admin = false;
    private String adminToken;
}
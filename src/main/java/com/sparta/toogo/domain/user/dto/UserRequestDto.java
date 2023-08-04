package com.sparta.toogo.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @Email(message = "이메일 양식이 아닙니다")
    @NotBlank(message = "Email 공백 불가")
    private String email;

    @NotBlank(message = "password 공백 불가")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,15}$", message = "비밀번호는 대소문자영문+숫자(8자이상~ 15자 이하) 형식으로 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임 공백 불가")
    @Size(min = 2, max = 15, message = "닉네임은 2글자 이상 15글자 이하로 입력해주세요.")
    private String nickname;

    private boolean admin = false;
    private String adminToken;
}
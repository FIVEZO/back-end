package com.sparta.toogo.domain.mypage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageRequestDto {
    @NotBlank(message = "닉네임을 입력해 주세요.")
    @Size(max = 15, message = "닉네임은 2글자 이상 15글자 이하로 입력해주세요.")
    private String newNickname;
    @Size(max = 70, message = "자기소개는 70자 이하로 입력해주세요.")
    private String newIntroduction;
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,15}$", message = "비밀번호는 대소문자영문+숫자(8자이상~ 15자 이하) 형식으로 입력해주세요.")
    private String password;
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,15}$", message = "비밀번호는 대소문자영문+숫자(8자이상~ 15자 이하) 형식으로 입력해주세요.")
    private String newPassword;
    private String newEmoticon;
}
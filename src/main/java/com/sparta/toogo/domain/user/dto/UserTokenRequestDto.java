package com.sparta.toogo.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserTokenRequestDto {
    @NotBlank
    private String accessToken;
}

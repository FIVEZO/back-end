package com.sparta.toogo.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.toogo.domain.kakao.service.KakaoService;
import com.sparta.toogo.domain.user.dto.LogInResponseDto;
import com.sparta.toogo.domain.user.dto.UserRequestDto;
import com.sparta.toogo.domain.user.dto.UserResponseDto;
import com.sparta.toogo.domain.user.dto.UserTokenRequestDto;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.service.UserService;
import com.sparta.toogo.global.jwt.JwtUtil;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.sparta.toogo.global.enums.SuccessCode.REGENERATED_TOKEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final KakaoService kakaoService;

    @Operation(summary = "회원 가입")
    @PostMapping("/signup")
    public ApiResponse<?> signUp(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseUtil.ok(userService.signUp(userRequestDto));
    }

    @Operation(summary = "이메일 확인", description = "등록된 이메일인지 확인합니다.")
    @PostMapping("/email")
    public Boolean checkeMail(@RequestParam String email) {
        return !userService.checkEmail(email);
    }

    @Operation(summary = "닉네임 확인", description = "등록된 닉네임인지 확인합니다.")
    @PostMapping("/nickname")
    public Boolean checkNickname(@RequestParam String nickname) {
        return !userService.checkNickname(nickname);
    }

    @Operation(summary = "카카오 로그인")
    @GetMapping("/kakao")
    public LogInResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        User user = kakaoService.kakaoLogin(code);
        String createAccessToken = kakaoService.kakaoAccessToken(user);
        String createRefreshToken = kakaoService.kakaoRefreshToken(user);
        jwtUtil.saveTokenToRedis(createRefreshToken, createAccessToken);
        jwtUtil.addTokenToHeader(createAccessToken, createRefreshToken, response);

        String email = user.getEmail();
        String nickname = user.getNickname();
        String emoticon = user.getEmoticon();
        return new LogInResponseDto(Objects.requireNonNullElse(email, ""), nickname, emoticon);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public UserResponseDto logOut(HttpServletRequest request) {
        return userService.logOut(request);
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/token")
    public UserResponseDto regenerateToken(@RequestBody UserTokenRequestDto userTokenRequestDto, HttpServletRequest req, HttpServletResponse res) {
        userService.regenerateToken(userTokenRequestDto.getAccessToken(), jwtUtil.getRefreshTokenFromHeader(req), res);
        return new UserResponseDto(REGENERATED_TOKEN);
    }
}
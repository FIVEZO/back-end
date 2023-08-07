package com.sparta.toogo.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.toogo.domain.kakao.service.KakaoService;
import com.sparta.toogo.domain.user.dto.UserRequestDto;
import com.sparta.toogo.domain.user.dto.UserResponseDto;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.service.UserService;
import com.sparta.toogo.global.jwt.JwtUtil;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.sparta.toogo.global.enums.SuccessCode.USER_LOGIN_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final KakaoService kakaoService;

    @PostMapping("/signup")
    public ApiResponse<?> signUp(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseUtil.ok(userService.signUp(userRequestDto));
    }

    @PostMapping("/email")
    public Boolean checkeMail(@RequestParam String email) {
        return !userService.checkEmail(email);
    }

    @PostMapping("/nickname")
    public Boolean checkNickname(@RequestParam String nickname) {
        return !userService.checkNickname(nickname);
    }

    @GetMapping("/kakao")
    public UserResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        User user = kakaoService.kakaoLogin(code);
        String createAccessToken = kakaoService.kakaoAccessToken(user);
        String createRefreshToken = kakaoService.kakaoRefreshToken(user);
        jwtUtil.saveTokenToRedis(createRefreshToken, createAccessToken);
        jwtUtil.addTokenToHeader(createAccessToken, createRefreshToken, response);
        return new UserResponseDto(USER_LOGIN_SUCCESS);
    }

    @PostMapping("/logout")
    public UserResponseDto logOut(HttpServletRequest request) {
        return userService.logOut(request);
    }
}
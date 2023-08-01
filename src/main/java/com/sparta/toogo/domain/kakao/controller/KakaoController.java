package com.sparta.toogo.domain.kakao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.toogo.domain.kakao.service.KakaoService;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class KakaoController {

    private final JwtUtil jwtUtil;
    private final KakaoService kakaoService;

    @GetMapping("/kakao")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        User user = kakaoService.kakaoLogin(code);
        String createAccessToken = kakaoService.kakaoAccessToken(user);
        String createRefreshToken = kakaoService.kakaoRefreshToken(user);
        jwtUtil.saveTokenToRedis(createRefreshToken, createAccessToken);
        jwtUtil.addTokenToHeader(createAccessToken, createRefreshToken, response);
        return "redirect:/";
    }
}
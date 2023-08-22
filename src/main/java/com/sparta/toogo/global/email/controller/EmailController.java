package com.sparta.toogo.global.email.controller;

import com.sparta.toogo.global.email.dto.EmailResponseDto;
import com.sparta.toogo.global.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/email")
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "이메일 인증 코드 전송")
    @PostMapping("/code")
    public EmailResponseDto confirmCode(@RequestParam String email) throws Exception {
        return emailService.sendSimpleMessage(email);
    }

    @Operation(summary = "이메일 인증 코드 확인")
    @PostMapping("/confirm")
    public Boolean confirmEmail(@RequestParam String code) {
        return emailService.checkCode(code);
    }

    @Operation(summary = "새 비밀번호 생성")
    @PostMapping("/find/password")
    public EmailResponseDto findPassword(@RequestParam String email) throws Exception {
        return emailService.sendNewPassword(email);
    }
}
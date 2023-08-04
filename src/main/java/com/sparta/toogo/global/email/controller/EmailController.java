package com.sparta.toogo.global.email.controller;

import com.sparta.toogo.global.email.dto.EmailResponseDto;
import com.sparta.toogo.global.email.service.EmailService;
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

    @PostMapping("/code")
    public EmailResponseDto confirmCode(@RequestParam String email) throws Exception {
        return emailService.sendSimpleMessage(email);
    }

    @PostMapping("/confirm")
    public Boolean confirmEmail(@RequestParam String code) {
        return emailService.checkCode(code);
    }
}

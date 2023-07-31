package com.sparta.toogo.domain.user.controller;

import com.sparta.toogo.domain.user.dto.UserRequestDto;
import com.sparta.toogo.domain.user.dto.UserResponseDto;
import com.sparta.toogo.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public UserResponseDto signUp(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.signUp(userRequestDto);
    }

    @PostMapping("/email")
    public Boolean checkeMail(@RequestParam String email) {
        return !userService.checkEmail(email);
    }

    @PostMapping("/nickname")
    public Boolean checkNickname(@RequestParam String nickname) {
        return !userService.checkNickname(nickname);
    }

    @PostMapping("/logout")
    public UserResponseDto logOut(HttpServletRequest request) {
        return userService.logOut(request);
    }
}
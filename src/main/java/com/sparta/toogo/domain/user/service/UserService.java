package com.sparta.toogo.domain.user.service;

import com.sparta.toogo.domain.mypage.entity.MyPage;
import com.sparta.toogo.domain.mypage.exception.MyPageException;
import com.sparta.toogo.domain.mypage.repository.MyPageRepository;
import com.sparta.toogo.domain.user.dto.UserRequestDto;
import com.sparta.toogo.domain.user.dto.UserResponseDto;
import com.sparta.toogo.domain.user.entity.EmoticonEnum;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.entity.UserRoleEnum;
import com.sparta.toogo.domain.user.exception.UserException;
import com.sparta.toogo.domain.user.repository.UserRepository;
import com.sparta.toogo.global.jwt.JwtUtil;
import com.sparta.toogo.global.redis.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.sparta.toogo.global.enums.ErrorCode.*;
import static com.sparta.toogo.global.enums.SuccessCode.*;

@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final MyPageRepository myPageRepository;
    private final JwtUtil jwtUtil;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    @Transactional
    public UserResponseDto signUp(UserRequestDto userRequestDto) {
        String email = userRequestDto.getEmail();
        String password = userRequestDto.getPassword();
        String nickname = userRequestDto.getNickname();
        String code = userRequestDto.getCode();

        if (redisService.getCode(code) == null || !Objects.equals(redisService.getCode(code), email)) {
            throw new UserException(EMAIL_CODE_INCOMPLETE);
        }

        if (checkEmail(email) || checkNickname(nickname)) {
            throw new UserException(DUPLICATE_RESOURCE);
        }

        if (!checkPassword(password)) {
            throw new MyPageException(INVALID_PASSWORD_FORMAT);
        }
        String encodingPassword = passwordEncoder.encode(password);

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userRequestDto.getAdminToken())) {
                throw new UserException(INVALID_ADMIN_NUMBER);
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(email, encodingPassword, nickname, role, EmoticonEnum.HAPPY.getEmoticon());
        userRepository.save(user);
        MyPage myPage = new MyPage();
        myPage.setUser(user);
        myPageRepository.save(myPage);
        redisService.deleteCode(code);
        return new UserResponseDto(USER_SIGNUP_SUCCESS);
    }

    public UserResponseDto logOut(HttpServletRequest req) {
        String accessToken = req.getHeader("accessToken");
        redisService.deleteToken(accessToken);
        return new UserResponseDto(LOGOUT_SUCCESS);
    }

    public void regenerateToken(String accessToken, String refreshToken, HttpServletResponse res) {
        jwtUtil.validateRegenerate(accessToken,refreshToken);
        jwtUtil.regenerateToken(accessToken, refreshToken, res);
    }

    public Boolean checkEmail(String email) {
        if (email == null || email.equals("")) {
            throw new UserException(EMAIL_REQUIRED);
        }
        return userRepository.existsByEmail(email);
    }

    public Boolean checkNickname(String nickname) {
        if (nickname == null || nickname.equals("") || nickname.length() > 15 || nickname.length() < 2) {
            throw new UserException(NICKNAME_LENGTH_INVALID);
        }

        if (!nickname.matches("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]*$")) {
            throw new UserException(NICKNAME_FORMAT_INVALID);
        }
        return userRepository.existsByNickname(nickname);
    }

    public Boolean checkPassword(String password) {
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).{8,15}$")) {
            throw new MyPageException(INVALID_PASSWORD_FORMAT);
        }
        return true;
    }
}
package com.sparta.toogo.domain.user.service;

import com.sparta.toogo.domain.mypage.entity.MyPage;
import com.sparta.toogo.domain.mypage.repository.MyPageRepository;
import com.sparta.toogo.domain.user.dto.UserRequestDto;
import com.sparta.toogo.domain.user.dto.UserResponseDto;
import com.sparta.toogo.domain.user.entity.EmotionEnum;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.entity.UserRoleEnum;
import com.sparta.toogo.domain.user.exception.UserException;
import com.sparta.toogo.domain.user.repository.UserRepository;
import com.sparta.toogo.global.redis.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.sparta.toogo.global.enums.ErrorCode.*;
import static com.sparta.toogo.global.enums.SuccessCode.LOGOUT_SUCCESS;
import static com.sparta.toogo.global.enums.SuccessCode.USER_SIGNUP_SUCCESS;

@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final MyPageRepository myPageRepository;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    @Transactional
    public UserResponseDto signUp(UserRequestDto userRequestDto) {
        String email = userRequestDto.getEmail();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        String nickname = userRequestDto.getNickname();
        String code = userRequestDto.getCode();

        if (redisService.getCode(code) == null || !Objects.equals(redisService.getCode(code), email)) {
            throw new UserException(EMAIL_CODE_INCOMPLETE);
        }

        if (checkEmail(email) || checkNickname(nickname)) {
            throw new UserException(DUPLICATE_RESOURCE);
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userRequestDto.getAdminToken())) {
                throw new UserException(INVALID_ADMIN_NUMBER);
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(email, password, nickname, role, EmotionEnum.HAPPY.getEmotion());
        userRepository.save(user);
        MyPage myPage = new MyPage();
        myPage.setUser(user);
        myPageRepository.save(myPage);
        redisService.deleteCode(code);
        return new UserResponseDto(USER_SIGNUP_SUCCESS, user);
    }

    public UserResponseDto logOut(HttpServletRequest req) {
        String accessToken = req.getHeader("AccessToken");
        redisService.deleteToken(accessToken);
        return new UserResponseDto(LOGOUT_SUCCESS);
    }

    public Boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean checkNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
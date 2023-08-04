package com.sparta.toogo.domain.user.service;

import com.sparta.toogo.domain.user.dto.UserRequestDto;
import com.sparta.toogo.domain.user.dto.UserResponseDto;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.entity.UserRoleEnum;
import com.sparta.toogo.domain.user.exception.UserException;
import com.sparta.toogo.domain.user.repository.UserRepository;
import com.sparta.toogo.global.jwt.JwtUtil;
import com.sparta.toogo.global.redis.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    @Transactional
    public UserResponseDto signUp(UserRequestDto userRequestDto) {
        String email = userRequestDto.getEmail();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        String nickname = userRequestDto.getNickname();
        String code = userRequestDto.getCode();

        if (code == null || !Objects.equals(redisService.getCode(code), email)) {
            throw new UserException(CODE_VERIFICATION_COMPLETED);
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
        User user = new User(email, password, nickname, role);

        userRepository.save(user);
        redisService.deleteCode(code);
        return new UserResponseDto(USER_SIGNUP_SUCCESS);
    }

    public UserResponseDto logOut(HttpServletRequest req) {
        String refreshToken = req.getHeader(jwtUtil.HEADER_REFRESH_TOKEN);
        redisService.deleteToken(refreshToken);
        return new UserResponseDto(LOGOUT_SUCCESS);
    }

    public Boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean checkNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
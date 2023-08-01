package com.sparta.toogo.domain.user.service;

import com.sparta.toogo.domain.user.dto.UserRequestDto;
import com.sparta.toogo.domain.user.dto.UserResponseDto;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.entity.UserRoleEnum;
import com.sparta.toogo.domain.user.repository.UserRepository;
import com.sparta.toogo.global.jwt.JwtUtil;
import com.sparta.toogo.global.redis.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    @Transactional
    public UserResponseDto signUp(UserRequestDto userRequestDto) {
        String email = userRequestDto.getEmail();
        String nickname = userRequestDto.getNickname();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        if (checkEmail(email) || checkNickname(nickname)) {
            throw new IllegalArgumentException("데이터가 이미 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 번호가 유효하지 않습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(role)
                .build();

        userRepository.save(user);
        return new UserResponseDto("가입 완료", HttpStatus.OK.value());
    }

    public Boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean checkNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public UserResponseDto logOut(HttpServletRequest req) {
        String refreshToken = req.getHeader(jwtUtil.HEADER_REFRESH_TOKEN);
        redisService.deleteToken(refreshToken);
        return new UserResponseDto("로그아웃 성공", HttpStatus.NO_CONTENT.value());
    }
}
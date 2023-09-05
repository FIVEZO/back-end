package com.sparta.toogo.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.toogo.domain.user.dto.LogInRequestDto;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.entity.UserRoleEnum;
import com.sparta.toogo.global.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LogInRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LogInRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        Long id = user.getId();
        String email = user.getEmail();
        String nickname = user.getNickname();
        UserRoleEnum role = user.getRole();
        String accessToken = jwtUtil.createAccessToken(id, role);
        String refreshToken = jwtUtil.createRefreshToken(id);
        jwtUtil.saveTokenToRedis(accessToken, refreshToken);
        jwtUtil.addTokenToHeader(accessToken, refreshToken, response);
        String emoticon = user.getEmoticon();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("statusCode", HttpServletResponse.SC_OK);
        data.put("msg", "로그인 성공");
        data.put("email", email);
        data.put("nickname", nickname);
        data.put("emoticon", emoticon);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("success", false);
        data.put("statusCode", HttpServletResponse.SC_BAD_REQUEST);

        if (failed instanceof UsernameNotFoundException) {
            data.put("msg", "등록되지 않은 이메일입니다.");
        } else if (failed instanceof BadCredentialsException) {
            data.put("msg", "이메일 또는 비밀번호를 확인해 주세요.");
        } else {
            data.put("msg", "로그인 실패");
        }

        // 에러 메시지를 JSON 형식으로 생성
        ObjectMapper objectMapper = new ObjectMapper();
        String errorJson = objectMapper.writeValueAsString(data);

        // 응답에 에러 메시지 전송
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(errorJson);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
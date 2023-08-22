package com.sparta.toogo.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.exception.UnauthorizedException;
import com.sparta.toogo.global.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessTokenValue = jwtUtil.getAccessTokenFromHeader(req);

        if (StringUtils.hasText(accessTokenValue)) {
            log.info(accessTokenValue);

            if (!jwtUtil.validateAccessToken(accessTokenValue)) {
                log.error("AccessToken 검증 실패");
                String refreshTokenValue = jwtUtil.getRefreshTokenFromHeader(req);
                if (StringUtils.hasText(refreshTokenValue)) {
                    // 유효한 refreshToken인지 검증
                    if (!jwtUtil.validateRegenerate(accessTokenValue, refreshTokenValue)) {
                        throw new IllegalArgumentException("RefreshToken 인증 실패");
                    }
                    try {
                        jwtUtil.regenerateToken(refreshTokenValue, res);
                        log.info("새로운 AccessToken, RefreshToken 발급 완료");
                        throw new UnauthorizedException(ErrorCode.REGENERATED_TOKEN);
                    } catch (UnauthorizedException e) {
                        log.error("토큰 재발급");
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                }
            }
            Claims info = jwtUtil.getUserInfo(accessTokenValue);
            try {
                setAuthentication(info.get("email", String.class));
            } catch (Exception e) {
                log.error("인증 오류");
            }
        }
        filterChain.doFilter(req, res);
    }

    // 인증 정보 설정
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
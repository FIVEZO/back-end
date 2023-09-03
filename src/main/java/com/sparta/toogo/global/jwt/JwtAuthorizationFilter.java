package com.sparta.toogo.global.jwt;

import com.sparta.toogo.global.jwt.exception.JwtCustomException;
import com.sparta.toogo.global.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.sparta.toogo.global.enums.ErrorCode.*;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    public final String HEADER_ACCESS_TOKEN = "AccessToken";
    public final String HEADER_REFRESH_TOKEN = "RefreshToken";

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.getAccessTokenFromHeader(req);

        if (StringUtils.hasText(accessToken)) {
            log.info(accessToken);

            if (!jwtUtil.validateAccessToken(accessToken)) {
                log.error("AccessToken 검증 실패");
                String refreshToken = jwtUtil.getRefreshTokenFromHeader(req);

                if (StringUtils.hasText(refreshToken)) {
                    log.info(refreshToken);

                    if (!jwtUtil.validateRegenerate(accessToken, refreshToken)) {
                        throw new JwtCustomException(INVALID_TOKEN);
                    }
                    try {
                        jwtUtil.regenerateToken(accessToken, refreshToken, res);
                        log.info("새로운 AccessToken, RefreshToken 발급 완료");
                        throw new JwtCustomException(REGENERATED_TOKEN);
                    } catch (JwtCustomException e) {
                        res.setStatus(418);
                        return;
                    }
                }
            }
            Claims info = jwtUtil.getUserInfo(accessToken);
            try {
                setAuthentication(info.get("email", String.class));
            } catch (Exception e) {
                throw new JwtCustomException(MISMATCH_TOKEN);
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
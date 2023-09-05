package com.sparta.toogo.global.jwt;

import com.sparta.toogo.global.jwt.exception.JwtCustomException;
import com.sparta.toogo.global.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.sparta.toogo.global.enums.ErrorCode.MISMATCH_TOKEN;

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
        String accessToken = jwtUtil.getAccessTokenFromHeader(req);
        String refreshToken = jwtUtil.getRefreshTokenFromHeader(req);

        if (accessToken != null) {
            if (StringUtils.hasText(accessToken)) {
                log.info(accessToken);

                if (!jwtUtil.validateAccessToken(accessToken, res)) {
                    log.error("엑세스 토큰 검증 실패");
                    return;
                }
                Claims info = jwtUtil.getUserInfo(accessToken);
                try {
                    setAuthentication(Long.valueOf(info.get("sub", String.class)));
                } catch (Exception e) {
                    throw new JwtCustomException(MISMATCH_TOKEN);
                }
            }
        } else if (refreshToken != null) {
            if (StringUtils.hasText(refreshToken)) {
                log.info(refreshToken);

                if (!jwtUtil.validateRefreshToken(refreshToken)) {
                    log.error("리프레시 토큰 검증 실패");
                    return;
                }
                Claims info = jwtUtil.getUserInfo(refreshToken);
                try {
                    setAuthentication(Long.valueOf(info.get("sub", String.class)));
                } catch (Exception e) {
                    throw new JwtCustomException(MISMATCH_TOKEN);
                }
            }
        }
        filterChain.doFilter(req, res);
    }

    // 인증 정보 설정
    public void setAuthentication(Long id) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(id);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(Long id) {
        UserDetails userDetails = userDetailsService.loadUserById(id);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
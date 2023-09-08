package com.sparta.toogo.global.jwt;

import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.domain.user.entity.UserRoleEnum;
import com.sparta.toogo.domain.user.exception.UserException;
import com.sparta.toogo.domain.user.repository.UserRepository;
import com.sparta.toogo.global.jwt.exception.JwtCustomException;
import com.sparta.toogo.global.redis.service.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.sparta.toogo.global.enums.ErrorCode.*;

@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final RedisTemplate<String, String> redisTemplate;

    public final String HEADER_ACCESS_TOKEN = "AccessToken";
    public final String HEADER_REFRESH_TOKEN = "RefreshToken";
    public final String AUTHORIZATION_KEY = "auth";
    private final String BEARER = "Bearer ";
    private final Long ACCESS_TOKEN_EXPIRATION_TIME = 6 * 60 * 60 * 1000L;
    private final Long REFRESSH_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 3000L;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private Key key;
    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT AccessToken 생성 메서드
    public String createAccessToken(Long id, UserRoleEnum role) {
        Date date = new Date();
        return BEARER +
                Jwts.builder()
                        .setSubject(String.valueOf(id)) // 토큰(사용자) 식별자 값
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRATION_TIME)) // 만료일
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 시크릿 키, 암호화 알고리즘
                        .compact();
    }

    // JWT RefreshToken 생성 메서드
    public String createRefreshToken(Long id) {
        Date date = new Date();
        return BEARER +
                Jwts.builder()
                        .setSubject(String.valueOf(id)) // 사용자 식별자값(ID)
                        .setExpiration(new Date(date.getTime() + REFRESSH_TOKEN_EXPIRATION_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 시크릿 키, 암호화 알고리즘
                        .compact();
    }

    // Header에 Token 추가
    public void addTokenToHeader(String accessToken, String refreshToken, HttpServletResponse response) {
        response.setHeader(HEADER_ACCESS_TOKEN, accessToken);
        response.setHeader(HEADER_REFRESH_TOKEN, refreshToken);
    }

    // Header에서 accessToken 가져오기
    public String getAccessTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(HEADER_ACCESS_TOKEN);
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }

    // Header에서 refreshToken 가져오기
    public String getRefreshTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(HEADER_REFRESH_TOKEN);
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }

    // JWT Bearer Substirng 메서드
    public String substringToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        throw new JwtCustomException(TOKEN_MISSING);
    }

    // JWT 토큰의 사용자 정보 가져오는 메서드
    public Claims getUserInfo(String tokenValue) {
        String token = substringToken(tokenValue);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT accessToken 검증 메서드
    public boolean validateAccessToken(String accessToken, HttpServletResponse res) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(substringToken(accessToken)); // key로 accessToken 검증
            return true;
        } catch (SecurityException | MalformedJwtException | io.jsonwebtoken.security.SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT accessToken, 만료된 JWT 토큰입니다.");
            res.setStatus(418);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT accessToken, 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    public boolean validateRefreshToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(substringToken(accessToken)); // key로 accessToken 검증
            return true;
        } catch (SecurityException | MalformedJwtException | io.jsonwebtoken.security.SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT accessToken, 만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT accessToken, 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    // AccessToken, RefreshToken 검증 메서드
    public boolean validateRegenerate(String accessToken, String refreshToken) {
        // token이 없을 경우
        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            log.error("엑세스 토큰 또는 리프레시 토큰이 존재하지 않습니다.");
            throw new JwtCustomException(TOKEN_MISSING);
        }

        if (!validateRefreshToken(refreshToken)) {
            throw new JwtCustomException(INVALID_TOKEN);
        }

        // redis에서 기존에 저장된 RefreshToken 조회
        String refreshTokenFromRedis = getRefressTokenFromRedis(accessToken);

        // 사용자가 보낸 Refresh Token과 최초 발급된 Refresh Token이 일치하지 않을 경우
        if (!substringToken(refreshToken).equals(refreshTokenFromRedis)) {
            log.error("RefreshToken이 위조되었습니다.");
            throw new JwtCustomException(REFRESH_TOKEN_FORGERY);
        }
        return true;
    }

    // AccessToken, RefreshToken 재발급 메서드
    public void regenerateToken(String accessToken, String refreshToken, HttpServletResponse res) {
        Long userId = Long.parseLong(getUserInfo(refreshToken).getSubject());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        UserRoleEnum userRole = user.getRole();

        String newAccessToken = createAccessToken(userId, userRole);
        String newRefreshToken = createRefreshToken(userId);

        saveTokenToRedis(newAccessToken, newRefreshToken);
        redisService.deleteToken(accessToken);
        addTokenToHeader(newAccessToken, newRefreshToken, res);
        log.info("토큰 재발급 성공");
        log.info("access : " + newAccessToken);
        log.info("refresh : " + newRefreshToken);
    }

    // Redis에 저장된 RefreshToken 반환 (key : access / value : refresh)
    public String getRefressTokenFromRedis(String accessToken) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return redisService.getRefreshToken(accessToken);
    }

    // 발급된 토큰 값 Redis에 저장 (key : access / value : refresh)
    public void saveTokenToRedis(String accessToken, String refreshToken) {
        Date refreshExpire = getUserInfo(refreshToken).getExpiration(); // refresh 토큰의 만료일
        String subRefreshToken = substringToken(refreshToken);
        redisService.saveAccessToken(accessToken, subRefreshToken, refreshExpire);
    }
}
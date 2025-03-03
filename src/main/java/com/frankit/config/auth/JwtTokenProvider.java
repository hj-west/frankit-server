package com.frankit.config.auth;

import com.frankit.config.properties.JwtTokenProperties;
import com.frankit.service.auth.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtTokenProperties jwtTokenProperties;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private Key secretKey;

    /**
     * SecretKey 초기화
     */
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtTokenProperties.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * JWT 토큰 생성
     *
     * @param userPk 유저 고유 식별자
     * @param email 유저 이메일
     * @param roles  유저의 역할
     * @return JWT 토큰 문자열
     */
    public String generateToken(Long userPk, String email, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
        claims.put("email", email);
        claims.put("roles", roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtTokenProperties.getTokenValidTime()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰에서 인증 정보 조회
     *
     * @param token JWT 토큰
     * @return Authentication 객체
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰에서 이메일 추출
     *
     * @param token JWT 토큰
     * @return 사용자 이메일
     */
    public String getUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    /**
     * JWT 토큰의 유효성 및 만료 확인
     *
     * @param token JWT 토큰
     * @return 유효 여부
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
            return false;
        }
    }
}

package com.example.carservice.security;

import com.example.carservice.dto.user.UserAppDTO;
import com.example.carservice.exceptions.AuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private final String accessSecret;
    private final String refreshSecret;
    private final Long accessExpirationPeriodInMinutes;
    private final Long refreshExpirationPeriodInDays;

    private final int hoursFromGMT = 3;
    @Value("${time.defaultZoneId}")
    private String defaultZoneId;

    public JwtProvider(@Value("${jwt.secret.access}") String accessSecret,
                      @Value("${jwt.secret.refresh}") String refreshSecret,
                      @Value("${jwt.time.accessExpirationPeriodInMinutes}") Long accessLifeInMinutes,
                      @Value("${jwt.time.refreshExpirationPeriodInDays}") Long refreshLifeInDays) {
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
        this.accessExpirationPeriodInMinutes = accessLifeInMinutes;
        this.refreshExpirationPeriodInDays = refreshLifeInDays;
    }

    public String generateAccessToken(UserAppDTO user) {
        LocalDateTime createdAt = LocalDateTime.now(ZoneId.of(defaultZoneId));
        LocalDateTime expiredAt = createdAt.plusMinutes(accessExpirationPeriodInMinutes);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(expiredAt.toInstant(ZoneOffset.ofHours(3))))
                .signWith(generateKey(accessSecret.getBytes()))
                .claim("roles", user.getAuthorities())
                .compact();
    }

    public String generateRefreshToken(UserAppDTO user) {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusDays(refreshExpirationPeriodInDays);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(expiredAt.toInstant(ZoneOffset.ofHours(3))))
                .signWith(generateKey(refreshSecret.getBytes()))
                .claim("roles", user.getAuthorities())
                .compact();
    }

    public boolean isAccessTokenValid(String accessToken) {
        if (accessToken == null) {
            logAuthExc("No access token provided");
        }
        return validateToken(accessToken, accessSecret);
    }

    public boolean isRefreshTokenValid(String refreshToken) {
        if (refreshToken == null) {
            logAuthExc("No refresh token provided");
        }
        return validateToken(refreshToken, refreshSecret);
    }

    private boolean validateToken(String token, String secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported jwt", e);
        } catch (MalformedJwtException e) {
            log.error("Malformed jwt", e);
        } catch (SignatureException e) {
            log.error("Invalid signature", e);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(String accessToken) {
        if (accessToken == null) {
            logAuthExc("No access token provided");
        }
        return getClaims(accessToken, accessSecret);
    }

    public Claims getRefreshTokenClaims(String refreshToken) {
        if (refreshToken == null) {
            logAuthExc("No refresh token provided");
        }
        return getClaims(refreshToken, refreshSecret);
    }

    private Claims getClaims(String token, String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey generateKey(byte[] bytes) {
        return Keys.hmacShaKeyFor(bytes);
    }

    private void logAuthExc(String message){
        log.error(message);
        throw new AuthenticationException(message);
    }
}

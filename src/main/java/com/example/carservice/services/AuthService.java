package com.example.carservice.services;

import com.example.carservice.dto.jwt.AccessTokenResponseDTO;
import com.example.carservice.dto.jwt.AuthRequest;
import com.example.carservice.dto.jwt.JwtResponseDTO;
import com.example.carservice.dto.user.UserAppDTO;
import com.example.carservice.exceptions.AuthenticationException;
import com.example.carservice.security.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final Map<String, String> refreshTokenStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public JwtResponseDTO login(AuthRequest authRequest) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword());
        try {
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException ex) {
            throw new AuthenticationException("Login or password is incorrect!");
        }
        UserAppDTO user = userService.loadUserByUsername(authRequest.getUsername());
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshTokenStorage.put(user.getUsername(), refreshToken);
        return new JwtResponseDTO(accessToken, refreshToken);
    }

    public AccessTokenResponseDTO getNewAccessToken(String refreshToken) throws AuthenticationException {
        if (jwtProvider.isRefreshTokenValid(refreshToken)) {
            Claims claims = jwtProvider.getRefreshTokenClaims(refreshToken);
            String username = claims.getSubject();
            String refreshTokenFromStorage = refreshTokenStorage.get(username);
            if (refreshTokenFromStorage != null && refreshTokenFromStorage.equals(refreshToken)) {
                UserAppDTO user = userService.loadUserByUsername(username);
                String accessToken = jwtProvider.generateAccessToken(user);
                return new AccessTokenResponseDTO(accessToken);
            }
        }
        throw new AuthenticationException("Refresh JWT is invalid!");
    }

    public JwtResponseDTO getNewRefreshToken(String refreshToken) throws AuthenticationException {
        if (jwtProvider.isRefreshTokenValid(refreshToken)) {
            Claims claims = jwtProvider.getRefreshTokenClaims(refreshToken);
            String username = claims.getSubject();
            String refreshTokenFromStorage = refreshTokenStorage.get(username);
            if (refreshTokenFromStorage != null && refreshTokenFromStorage.equals(refreshToken)) {
                UserAppDTO user = userService.loadUserByUsername(username);
                String accessToken = jwtProvider.generateAccessToken(user);
                String newRefreshToken = jwtProvider.generateRefreshToken(user);
                return new JwtResponseDTO(accessToken, newRefreshToken);
            }
        }
        throw new AuthenticationException("Refresh JWT is invalid!");
    }
}
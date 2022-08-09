package com.example.carservice.services;

import com.example.carservice.dto.user.AppUserDTO;
import com.example.carservice.entities.User;
import com.example.carservice.repos.UserRepo;
import com.example.carservice.security.CustomUserDetails;
import com.example.carservice.security.JwtProvider;
import com.example.carservice.security.JwtRequest;
import com.example.carservice.security.JwtResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    private final Map<String, String> jwtRefreshTokenStorage = new HashMap<>();
    private final UserRepo userRepo;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword());
        try {
            authManager.authenticate(token);
        } catch (BadCredentialsException ex) {
            throw new AuthenticationException("Login/password is incorrect!");
        }
        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(authRequest.getLogin());
        String accessToken = jwtProvider.generateAccessToken(user.getUser());
        String refreshToken = jwtProvider.generateRefreshToken(user.getUser());
        jwtRefreshTokenStorage.put(user.getUsername(), refreshToken);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                User user = userRepo.findUserByEmail(login)
                        .orElseThrow(() -> new AuthException("Unable to find user with such name!"));
                String accessToken = jwtProvider.generateAccessToken(modelMapper.map(user, AppUserDTO.class));
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                User user = userRepo.findUserByEmail(login)
                        .orElseThrow(() -> new AuthException("Unable to find user with such name!"));
                final String accessToken = jwtProvider.generateAccessToken(modelMapper.map(user, AppUserDTO.class));
                final String newRefreshToken = jwtProvider.generateRefreshToken(modelMapper.map(user, AppUserDTO.class));
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("JWT is not valid!");
    }

    public CustomUserDetails getAuthInfo() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

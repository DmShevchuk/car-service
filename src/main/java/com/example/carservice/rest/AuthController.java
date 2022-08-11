package com.example.carservice.rest;

import com.example.carservice.dto.jwt.AccessTokenResponseDTO;
import com.example.carservice.dto.jwt.AuthRequest;
import com.example.carservice.dto.jwt.JwtResponseDTO;
import com.example.carservice.dto.jwt.RefreshRequestDTO;
import com.example.carservice.dto.user.UserDTO;
import com.example.carservice.dto.user.UserSaveDTO;
import com.example.carservice.entities.User;
import com.example.carservice.services.AuthService;
import com.example.carservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;
import javax.validation.Valid;

/**
 * Контроллер для выполнения авторизации пользователей в системе
 * */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registration(@Valid @RequestBody UserSaveDTO userSaveDTO){
        User user = modelMapper.map(userSaveDTO, User.class);
        return modelMapper.map(userService.registration(user), UserDTO.class);
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponseDTO login(@Valid @RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }


    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public AccessTokenResponseDTO getNewAccessToken(@Valid @RequestBody RefreshRequestDTO request){
        return authService.getNewAccessToken(request.getRefreshToken());
    }


    @PostMapping("/refresh")
    public JwtResponseDTO getNewRefreshToken(@Valid @RequestBody RefreshRequestDTO request){
        return authService.getNewRefreshToken(request.getRefreshToken());
    }
}

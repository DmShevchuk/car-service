package com.example.carservice.rest;

import com.example.carservice.dto.user.UserDTO;
import com.example.carservice.dto.user.UserSaveDTO;
import com.example.carservice.entities.User;
import com.example.carservice.security.JwtRequest;
import com.example.carservice.security.JwtResponse;
import com.example.carservice.security.RefreshJwtRequest;
import com.example.carservice.services.AuthService;
import com.example.carservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registration(@Valid @RequestBody UserSaveDTO userSaveDTO){
        User user = modelMapper.map(userSaveDTO, User.class);
        return modelMapper.map(userService.registration(user), UserDTO.class);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse login(@RequestBody JwtRequest authRequest) throws AuthenticationException {
        return authService.login(authRequest);
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        return authService.getAccessToken(request.getRefreshToken());
    }

    @PostMapping("/refresh")
    public JwtResponse getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        return authService.refresh(request.getRefreshToken());
    }
}

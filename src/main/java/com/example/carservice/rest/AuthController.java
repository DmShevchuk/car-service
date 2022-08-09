package com.example.carservice.rest;

import com.example.carservice.dto.user.UserDTO;
import com.example.carservice.dto.user.UserSaveDTO;
import com.example.carservice.entities.User;
import com.example.carservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registration(@Valid @RequestBody UserSaveDTO userSaveDTO){
        User user = modelMapper.map(userSaveDTO, User.class);
        return modelMapper.map(userService.registration(user), UserDTO.class);
    }
}

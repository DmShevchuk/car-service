package com.example.carservice.rest;

import com.example.carservice.dto.user.PasswordRecoveryDTO;
import com.example.carservice.dto.user.UserDTO;
import com.example.carservice.dto.user.UserSaveDTO;
import com.example.carservice.entities.User;
import com.example.carservice.services.PasswordResetService;
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
    private final PasswordResetService passwordResetService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registration(@Valid @RequestBody UserSaveDTO userSaveDTO){
        User user = modelMapper.map(userSaveDTO, User.class);
        return UserDTO.toDTO(userService.registration(user));
    }

    @PostMapping("/pass-recovery")
    @ResponseStatus(HttpStatus.OK)
    public String recoverPassword(@Valid @RequestBody PasswordRecoveryDTO passwordRecoveryDTO){
        passwordResetService.resetPassword(passwordRecoveryDTO);
        return String.format("Link to confirm password recovery has been sent to '%s' !",
                passwordRecoveryDTO.getEmail());
    }

    @GetMapping("/pass-recovery/{token}")
    @ResponseStatus(HttpStatus.OK)
    public String confirmPasswordRecovery(@PathVariable String token){
        passwordResetService.confirmRecovery(token);
        return "Password was changed successfully!";
    }
}

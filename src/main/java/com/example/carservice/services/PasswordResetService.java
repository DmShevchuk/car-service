package com.example.carservice.services;

import com.example.carservice.dto.user.PasswordRecoveryDTO;
import com.example.carservice.entities.PasswordReset;
import com.example.carservice.entities.User;
import com.example.carservice.repos.PasswordResetRepo;
import com.example.carservice.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetRepo passwordResetRepo;
    private final UserRepo userRepo;
    private final UserService userService;
    @Value("${time.defaultZoneId}")
    private String zoneId;

    @Value("${time.expirationPeriodInMinutes}")
    private Long expirationPeriod;

    public void resetPassword(PasswordRecoveryDTO passwordRecoveryDTO){
        User user = userRepo.findUserByEmail(passwordRecoveryDTO.getEmail())
                .orElseThrow(RuntimeException::new);

        String password = passwordRecoveryDTO.getPassword();
        String passwordConfirm = passwordRecoveryDTO.getPasswordConfirm();

        if (!password.equals(passwordConfirm)){
            throw new RuntimeException();
        }

        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setExpireAt(LocalDateTime.now(ZoneId.of(zoneId)).plusMinutes(expirationPeriod));
        passwordReset.setUser(user);
        passwordReset.setToken(generateConfirmationToken());
        passwordResetRepo.save(passwordReset);
    }


    private String generateConfirmationToken(){
        int tokenLength = 30;
        boolean useLetters = true;
        boolean useNumbers = true;

        String token = RandomStringUtils.random(tokenLength, useLetters, useNumbers);
        System.out.println("localhost:8080/api/v1/auth/pass-recovery/" + token);
        return token;
    }

    public void confirmRecovery(String token) {
        PasswordReset passwordReset = passwordResetRepo.findPasswordResetByToken(token)
                .orElseThrow(RuntimeException::new);
        User user = passwordReset.getUser();
        userService.changePassword(user, passwordReset.getNewPassword());
        passwordResetRepo.delete(passwordReset);
    }
}

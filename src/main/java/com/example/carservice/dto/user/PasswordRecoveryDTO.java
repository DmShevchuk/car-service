package com.example.carservice.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PasswordRecoveryDTO {
    @NotNull(message = "Email not specified!")
    @Pattern(regexp = "\\w+@\\w+\\.\\w+", message = "Email doesn't match the pattern!")
    private String email;

    @NotBlank(message = "Password not specified!")
    @Size(min = 8, message = "Password is too short: minimum size is 8!")
    private String password;

    @NotBlank(message = "Password confirm not specified!")
    @Size(min = 8, message = "Password is too short: minimum size is 8!")
    private String passwordConfirm;
}

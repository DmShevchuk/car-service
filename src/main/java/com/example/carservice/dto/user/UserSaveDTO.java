package com.example.carservice.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveDTO {
    @NotBlank(message = "Name not specified!")
    private String userName;

    private String middleName;

    @NotBlank(message = "Last name not specified!")
    private String lastName;

    @NotNull(message = "Email not specified!")
    @Pattern(regexp = "\\w+@\\w+\\.\\w+", message = "Email doesn't match the pattern!")
    private String email;

    @NotBlank(message = "Password not specified!")
    @Size(min = 8, message = "Password is too short: minimum size is 8!")
    private String password;
}
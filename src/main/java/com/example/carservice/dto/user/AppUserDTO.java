package com.example.carservice.dto.user;

import com.example.carservice.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDTO {
    private Long id;
    private String name;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
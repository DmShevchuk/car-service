package com.example.carservice.dto.user;

import com.example.carservice.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserPatchDTO {
    @NotNull(message = "New user role should be specified!")
    private Role newRole;
}

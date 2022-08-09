package com.example.carservice.dto.user;

import com.example.carservice.entities.enums.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserPatchDTO {
    @NotNull(message = "New user role should be specified!")
    private RoleEnum newRole;
}

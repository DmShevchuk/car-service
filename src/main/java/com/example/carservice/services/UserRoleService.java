package com.example.carservice.services;

import com.example.carservice.entities.Role;
import com.example.carservice.entities.enums.RoleEnum;
import com.example.carservice.exceptions.UserRoleNotFoundException;
import com.example.carservice.repos.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepo roleRepo;

    public Role getRoleByName(RoleEnum roleEnum){
        return roleRepo.findByRoleName(roleEnum.toString())
                .orElseThrow(() -> new UserRoleNotFoundException(roleEnum.toString()));
    }
}

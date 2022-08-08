package com.example.carservice.services;

import com.example.carservice.entities.User;
import com.example.carservice.entities.enums.RoleEnum;
import com.example.carservice.exceptions.EmailAlreadyExistsException;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final UserRoleService roleService;

    @Transactional
    public User registration(User userEntity) {
        if (userRepo.existsByEmail(userEntity.getEmail())) {
            throw new EmailAlreadyExistsException(userEntity.getEmail());
        }
        userEntity.setRole(roleService.getRoleByName(RoleEnum.ROLE_USER.toString()));
        return userRepo.save(userEntity);
    }

    public Page<User> getAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }


    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Transactional
    public User update(Long id, User user) {
        user.setId(id);
        return userRepo.save(user);
    }

    @Transactional
    public void remove(Long id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public User changeRole(Long id, String roleName) {
        User user = getUserById(id);
        user.setRole(roleService.getRoleByName(roleName));
        return userRepo.save(user);
    }

    @Transactional
    public void changePassword(User user, String password) {
        user.setPassword(password);
        userRepo.save(user);
    }
}

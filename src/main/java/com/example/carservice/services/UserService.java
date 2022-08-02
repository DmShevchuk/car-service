package com.example.carservice.services;

import com.example.carservice.entities.User;
import com.example.carservice.exceptions.EmailAlreadyExistsException;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User registration(User userEntity) {
        if (userRepo.existsByEmail(userEntity.getEmail())) {
            throw new EmailAlreadyExistsException(userEntity.getEmail());
        }
        return userRepo.save(userEntity);
    }


    public Page<User> getAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }


    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    public User update(Long id, User user) {
        user.setId(id);
        return userRepo.save(user);
    }

    public void remove(Long id) {
        userRepo.deleteById(id);
    }
}

package com.example.carservice.services;

import com.example.carservice.dto.user.UserAppDTO;
import com.example.carservice.entities.User;
import com.example.carservice.exceptions.EmailAlreadyExistsException;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.UserRepo;
import com.example.carservice.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registration(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepo.save(user);
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
    public User changeRole(Long id, Role role) {
        User user = getUserById(id);
        user.setRole(role);
        return userRepo.save(user);
    }

    public User findUserByEmail(String email){
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public UserAppDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByEmail(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));
        return new UserAppDTO(user.getEmail(), user.getPassword(), authorities);
    }
}

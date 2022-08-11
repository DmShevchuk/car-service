package com.example.carservice.services;

import com.example.carservice.security.UserAppDTO;
import com.example.carservice.entities.User;
import com.example.carservice.exceptions.entities.EmailAlreadyExistsException;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
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


/**
 * Сервис для работы с пользователем
 * */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    /**
     * Регистрация пользователя с проверкой email на уникальность
     * */
    @Transactional
    public User registration(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepo.save(user);
    }



    /**
     * Получение всех пользователей
     * */
    public Page<User> getAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }



    /**
     * Получение пользователя по id
     **/
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }



    /**
     * Обновление пользователя
     * */
    @Transactional
    public User update(Long id, User user) {
        if (user.getEmail() != null & userRepo.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        user.setId(id);
        return userRepo.save(user);
    }



    /**
     * Сменя роли пользователя
     * */
    @Transactional
    public User changeRole(Long id, Role role) {
        User user = getUserById(id);
        user.setRole(role);
        return userRepo.save(user);
    }



    /**
     * Поиск пользователя по имени
     * */
    public User findUserByEmail(String email){
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }



    /**
     * Загрузка пользователя по имени и получение UserAppDTO для работы с security
     * */
    public UserAppDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByEmail(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));
        return new UserAppDTO(user.getEmail(), user.getPassword(), authorities);
    }
}

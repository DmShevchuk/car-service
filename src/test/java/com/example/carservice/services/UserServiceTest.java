package com.example.carservice.services;

import com.example.carservice.entities.User;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
import com.example.carservice.repos.UserRepo;
import com.example.carservice.security.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


public class UserServiceTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder encoder;

    private final UserService userService;
    private final User user;
    private final String password = "qwerty12345";
    private final Long id = 1L;
    private final String email = "example@mail.ru";


    public UserServiceTest() {
        String name = "Name";
        String middleName = "Middle name";
        String lastName = "Last name";

        MockitoAnnotations.openMocks(this);

        userService = new UserService(userRepo, encoder);
        user = new User();
        user.setId(id);
        user.setUserName(name);
        user.setEmail(email);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setPassword(password);
    }


    @Test
    @DisplayName("Save new user")
    void registration_WithCorrectData() {
        Mockito
                .doReturn(user)
                .when(userRepo)
                .save(user);

        String passwordEncode = "qwerty12345";
        Mockito.when(encoder.encode(password)).thenReturn(passwordEncode);
        Long userId = userService.registration(user).getId();

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(encoder, Mockito.times(1)).encode(password);

        Assertions.assertEquals(id, userId);
        Assertions.assertEquals(Role.ROLE_USER, user.getRole());
        Assertions.assertEquals(passwordEncode, user.getPassword());
    }


    @Test
    @DisplayName("Find user by existing id")
    void findUserById_CorrectResult() {
        Mockito
                .doReturn(Optional.ofNullable(user))
                .when(userRepo)
                .findById(id);

        User userBd = userService.getUserById(id);
        Mockito.verify(userRepo, Mockito.times(1)).findById(id);
        assert user != null;
        Assertions.assertEquals(user.getId(), userBd.getId());
    }

    @Test
    @DisplayName("Find user by existing email")
    void getUserByEmail_CorrectResult() {
        Mockito
                .doReturn(Optional.ofNullable(user))
                .when(userRepo)
                .findUserByEmail(email);
        User userBd = userService.findUserByEmail(email);
        Mockito.verify(userRepo, Mockito.times(1)).findUserByEmail(email);
        assert user != null;
        Assertions.assertEquals(user.getId(), userBd.getId());
    }


    @Test
    @DisplayName("Find user by non-existing email")
    void findUserByEmail_UserNotFoundException() {
        Mockito.doReturn(Optional.empty())
                .when(userRepo)
                .findUserByEmail(email);
        Assertions
                .assertNotNull(
                        Assertions.assertThrows(
                                UsernameNotFoundException.class, () -> userService.findUserByEmail(email)
                        ));
    }


    @Test
    @DisplayName("Find user by non-existing id")
    void findUserById_UserNotFoundException() {
        Mockito
                .doReturn(Optional.empty())
                .when(userRepo)
                .findById(id);
        Assertions
                .assertNotNull(
                        Assertions.assertThrows(
                                EntityNotFoundException.class, () -> userService.getUserById(id)
                        )
                );
    }
}

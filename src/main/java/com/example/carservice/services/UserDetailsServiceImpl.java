package com.example.carservice.services;

import com.example.carservice.dto.user.AppUserDTO;
import com.example.carservice.entities.User;
import com.example.carservice.repos.UserRepo;
import com.example.carservice.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email %s doesn't exist!", email)));
        AppUserDTO appUserDTO = modelMapper.map(user, AppUserDTO.class);
        return new CustomUserDetails(appUserDTO);
    }
}

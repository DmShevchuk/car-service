package com.example.carservice.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDTO {
    private String accessToken;
    private String refreshToken;
}

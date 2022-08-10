package com.example.carservice.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenResponseDTO {
    private String accessToken;
}

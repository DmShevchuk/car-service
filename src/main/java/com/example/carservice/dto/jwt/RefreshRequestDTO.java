package com.example.carservice.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequestDTO {
    private String refreshToken;
}

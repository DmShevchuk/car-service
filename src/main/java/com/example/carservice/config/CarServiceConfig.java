package com.example.carservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarServiceConfig {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}

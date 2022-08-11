package com.example.carservice.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для отправки служебной информации
 * */
@RestController
@RequestMapping("/info")
public class ApiInfoController {

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Проверка доступности сервера")
    public void sendHealth() {
    }


    @GetMapping("/version")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Информация о версиях API")
    public String sendVersion() {
        return "{" +
                "\"allVersions\": [\"v1\"], " +
                "\"latest\": \"v1\"" +
                "}";
    }
}

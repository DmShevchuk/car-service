package com.example.carservice.services.factories;

import com.example.carservice.entities.Confirmation;
import com.example.carservice.entities.Order;
import com.example.carservice.services.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Создание объектов класса {@link Confirmation} для подтверждения заказа
 * */
@Service
@RequiredArgsConstructor
public class ConfirmationFactory {

    private final ConfirmationService confirmationService;

    @Value("${time.defaultZoneId}")
    private String zoneId;
    @Value("${time.expirationPeriodInMinutes}")
    private Long expirationPeriod;


    /**
     * Создание токена подтверждения, которое становиться невалидным через 15 минут
     * */
    public void createConfirmation(Order order){
        Confirmation confirmation = new Confirmation();
        confirmation.setToken(generateConfirmationToken());
        confirmation.setOrder(order);
        confirmation.setExpireAt(LocalDateTime.now(ZoneId.of(zoneId)).plusMinutes(expirationPeriod));
        confirmationService.create(confirmation);
    }


    /**
     * Генератор токена для подтверждения
     * */
    private String generateConfirmationToken(){
        int tokenLength = 20;
        boolean useLetters = true;
        boolean useNumbers = true;

        String token = RandomStringUtils.random(tokenLength, useLetters, useNumbers);
        sendTokenToConsole(token);
        return token;
    }


    /**
     * Вывод ссылки на подтверждение заказа в консоль
     * */
    private void sendTokenToConsole(String token){
        System.out.println("--------------------------------------------");
        System.out.println("localhost:8080/api/v1/confirmation/" + token);
        System.out.println("--------------------------------------------");
    }
}

package com.example.carservice.services;

import com.example.carservice.entities.Confirmation;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.exceptions.ConfirmationNotFoundException;
import com.example.carservice.exceptions.ConfirmationTokenExpireException;
import com.example.carservice.repos.ConfirmationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ConfirmationService {
    private final ConfirmationRepo confirmationRepo;

    @Value("${time.defaultZoneId}")
    private String zoneId;

    @Transactional
    public void create(Confirmation confirmation){
        confirmationRepo.save(confirmation);
    }


    public Long confirmOrder(String token){
        Confirmation confirmation = confirmationRepo
                .getConfirmationByToken(token)
                .orElseThrow(() -> new ConfirmationNotFoundException(token));
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of(zoneId));

        LocalDateTime confirmationExpireAt = confirmation.getExpireAt();
        if (currentDateTime.compareTo(confirmationExpireAt) >= 0){
            throw new ConfirmationTokenExpireException(token);
        }
        delete(confirmation);
        return confirmation.getOrder().getId();
    }

    @Transactional
    public void delete(Confirmation confirmation){
        confirmationRepo.delete(confirmation);
    }
}

package com.example.carservice.services;

import com.example.carservice.entities.Confirmation;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.exceptions.ConfirmationNotFoundException;
import com.example.carservice.exceptions.ConfirmationTokenExpireException;
import com.example.carservice.repos.ConfirmationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ConfirmationService {
    private final ConfirmationRepo confirmationRepo;
    private final OrderService orderService;

    public void createConfirmation(Confirmation confirmation){
        confirmationRepo.save(confirmation);
    }

    public Order confirmOrder(String token){
        Confirmation confirmation = confirmationRepo
                .getConfirmationByToken(token)
                .orElseThrow(() -> new ConfirmationNotFoundException(token));
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        LocalDateTime confirmationExpireAt = confirmation.getExpireAt();
        if (currentDateTime.compareTo(confirmationExpireAt) >= 0){
            throw new ConfirmationTokenExpireException(token);
        }
        confirmation.setConfirmed(true);
        updateConfirmation(confirmation);
        return orderService.changeStatus(confirmation.getOrder().getId(), OrderStatusEnum.CONFIRMED.toString());
    }

    public void updateConfirmation(Confirmation confirmation){
        confirmationRepo.save(confirmation);
    }
}

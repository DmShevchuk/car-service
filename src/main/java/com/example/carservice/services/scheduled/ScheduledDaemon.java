package com.example.carservice.services.scheduled;

import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.repos.ConfirmationRepo;
import com.example.carservice.repos.OrderRepo;
import com.example.carservice.services.OrderService;
import com.example.carservice.specification.impl.OrderConfirmSpecificationFactoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class ScheduledDaemon {
    private final OrderRepo orderRepo;
    private final OrderService orderService;
    private final ConfirmationRepo confirmationRepo;
    private final OrderConfirmSpecificationFactoryImpl orderConfirmSpecificationFactory;

    @Async
    @Transactional
    @Scheduled(cron = "0 * * ? * *")
    public void updateOrderConfirmations() {
        confirmationRepo
                .findAll(orderConfirmSpecificationFactory
                        .createSpecificationForOrderConfirm(LocalDateTime.now(ZoneId.of("Europe/Moscow"))))
                .forEach(confirm ->
                {
                    orderService.changeStatus(confirm.getOrder().getId(), OrderStatusEnum.CANCELED.toString());
                    confirmationRepo.delete(confirm);
                });
        log.info("Orders confirmation updated successfully!");
    }


    @Async
    @Transactional
    @Scheduled(cron = "0 * * ? * *")
    public void updateOrderStatuses() {
        LocalDateTime timestamp = LocalDateTime.now();

        int year = timestamp.getYear();
        int month = timestamp.getMonthValue();
        int day = timestamp.getDayOfMonth();
        int hour = timestamp.getHour();
        int minutes = timestamp.getMinute();
        orderRepo.updateConfirmedToCanceled(hour, minutes, year, month, day);
        orderRepo.updateCheckedInToInProgress(hour, minutes, year, month, day);
        orderRepo.updateInProgressToFinished(hour, minutes, year, month, day);
        log.info("Order statuses updated successfully!");
    }
}

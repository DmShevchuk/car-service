package com.example.carservice.entities.enums;
import com.example.carservice.entities.Order;


/**
 * Enum для статуса заказов {@link Order}
 * */
public enum OrderStatusEnum {
    /**
     * Заказ ожидает подтверждения
     * */
    AWAITING_CONFIRMATION,


    /**
     * Заказ подтвержден
     * */
    CONFIRMED,


    /**
     * Клиент приехал заранее и отметился
     * */
    CLIENT_CHECKED_IN,


    /**
     * Заказ выполняется
     * */
    IN_PROGRESS,


    /**
     * Заказ отменен
     * */
    CANCELED,


    /**
     * Заказ успешно завершен
     * */
    FINISHED
}

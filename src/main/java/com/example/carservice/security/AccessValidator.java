package com.example.carservice.security;

import com.example.carservice.entities.Box;
import com.example.carservice.entities.Employee;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.User;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
import com.example.carservice.repos.UserRepo;
import com.example.carservice.services.BoxService;
import com.example.carservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.example.carservice.rest.EmployeeController;
import javax.validation.constraints.NotNull;

/**
 * Класс-валидатор доступа к данным
 * */
@Component
@RequiredArgsConstructor
public class AccessValidator {
    private final UserRepo userRepo;
    private final OrderService orderService;
    private final BoxService boxService;


    /**
     * Может ли оператор получить доступ к данным {@link EmployeeController}
     * */
    public boolean canGetInfo(@NotNull Long employeeId) {
        User user = userRepo.findUserByEmail(getPrincipalName())
                .orElseThrow(() -> new EntityNotFoundException("Employee", employeeId));
        return user.getEmployee()
                .getId()
                .equals(employeeId);
    }


    /**
     * Может ли пользователь с ролью ROLE_USER/ROLE_OPERATOR изменять заказ
     * */
    public boolean canChangeOrder(@NotNull Long orderId) {
        User user = userRepo.findUserByEmail(getPrincipalName())
                .orElseThrow(() -> new EntityNotFoundException("User", getPrincipalName()));

        Order order = orderService.getOrderById(orderId);

        if (user.getRole().equals(Role.ROLE_USER)) {
            return order.getUser().equals(user);
        }

        Employee employee = user.getEmployee();
        return employee.getBox().equals(order.getBox());

    }


    /**
     * Имеет ли оператор доступ к боксу
     * */
    public boolean operatorHasAccessToBox(@NotNull Long boxId) {
        User user = userRepo.findUserByEmail(getPrincipalName())
                .orElseThrow(() -> new EntityNotFoundException("User", getPrincipalName()));
        if (Role.ROLE_OPERATOR.equals(user.getRole())){
            Box box = boxService.getBoxById(boxId);
            return box.equals(user.getEmployee().getBox());
        }
        return true;
    }


    /**
     * Может ли текущий пользователь системы обновить пользователя
     * */
    public boolean canWorkWithUser(@NotNull Long userId) {
        User user = userRepo.findUserByEmail(getPrincipalName())
                .orElseThrow(() -> new EntityNotFoundException("User", getPrincipalName()));
        return userId.equals(user.getId());
    }


    /**
     * Получить email текущего пользователя
     * */
    private String getPrincipalName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}

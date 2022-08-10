package com.example.carservice.security;

import com.example.carservice.entities.Box;
import com.example.carservice.entities.Employee;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.User;
import com.example.carservice.repos.UserRepo;
import com.example.carservice.services.BoxService;
import com.example.carservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Component
@RequiredArgsConstructor
public class AccessValidator {
    private final UserRepo userRepo;
    private final OrderService orderService;
    private final BoxService boxService;

    public boolean canGetInfo(@NotNull Long employeeId) {
        User user = userRepo.findUserByEmail(getPrincipalName())
                .orElseThrow(() -> new RuntimeException("Unable to find user!"));
        return user.getEmployee()
                .getId()
                .equals(employeeId);
    }

    public boolean canChangeOrder(@NotNull Long orderId) {
        User user = userRepo.findUserByEmail(getPrincipalName())
                .orElseThrow(() -> new RuntimeException("Unable to find user!"));

        Order order = orderService.getOrderById(orderId);

        if (user.getRole().equals(Role.ROLE_USER)) {
            return order.getUser().equals(user);
        }

        Employee employee = user.getEmployee();
        return employee.getBox().equals(order.getBox());

    }

    public boolean operatorHasAccessToBox(@NotNull Long boxId) {
        User user = userRepo.findUserByEmail(getPrincipalName())
                .orElseThrow(() -> new RuntimeException("Unable to find user!"));
        if (Role.ROLE_OPERATOR.equals(user.getRole())){
            Box box = boxService.getBoxById(boxId);
            return box.equals(user.getEmployee().getBox());
        }
        return true;
    }

    public boolean canWorkWithUser(@NotNull Long userId) {
        User user = userRepo.findUserByEmail(getPrincipalName())
                .orElseThrow(() -> new RuntimeException("Unable to find user!"));
        return userId.equals(user.getId());
    }

    private String getPrincipalName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}

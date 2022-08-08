package com.example.carservice.rest;

import com.example.carservice.dto.order.OrderDTO;
import com.example.carservice.dto.user.UserDTO;
import com.example.carservice.dto.user.UserSaveDTO;
import com.example.carservice.entities.Order;
import com.example.carservice.entities.User;
import com.example.carservice.entities.enums.OrderStatusEnum;
import com.example.carservice.services.OrderService;
import com.example.carservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDTO> getAllUsers(@PageableDefault Pageable pageable){
        Page<User> users = userService.getAll(pageable);
        return users.map(u -> modelMapper.map(u, UserDTO.class));
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDTO> getAllUserOrders(@PathVariable Long id,
                                           @RequestParam("orderStatus") OrderStatusEnum orderStatus,
                                           @PageableDefault Pageable pageable){
        User user = userService.getUserById(id);
        Page<Order> orders = orderService.getAllByUserAndStatus(user, orderStatus, pageable);
        return orders.map(OrderDTO::toDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Long id){
        return UserDTO.toDTO(userService.getUserById(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUserById(@PathVariable Long id,
                                  @Valid @RequestBody UserSaveDTO userSaveDTO){
        User user = modelMapper.map(userSaveDTO, User.class);
        return UserDTO.toDTO(userService.update(id, user));
    }

    @PatchMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO changeUserRole(@PathVariable Long id,
                                  @RequestParam String roleName){
        return UserDTO.toDTO(userService.changeRole(id, roleName));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUserById(@PathVariable Long id){
        userService.remove(id);
        return "User was deleted successfully!";
    }
}

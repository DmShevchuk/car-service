package com.example.carservice.services;

import com.example.carservice.entities.Order;
import com.example.carservice.entities.ServiceType;
import com.example.carservice.exceptions.entities.EntityNotFoundException;
import com.example.carservice.repos.OrderRepo;
import com.example.carservice.services.factories.ConfirmationFactory;
import com.example.carservice.services.factories.OrderFactory;
import com.example.carservice.specification.IncomeSpecificationFactory;
import com.example.carservice.specification.OrderSpecificationFactory;
import com.example.carservice.specification.impl.CommonSpecificationBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OrderServiceTest {
    @Mock
    private OrderRepo orderRepo;
    private OrderFactory orderFactory;
    private ConfirmationFactory confirmationFactory;
    private OrderStatusService orderStatusService;
    private IncomeSpecificationFactory incomeSpecificationFactory;
    private OrderSpecificationFactory orderSpecificationFactory;
    private CommonSpecificationBuilder specificationBuilder;

    private final OrderService orderService;
    private final Order order;
    private final Long orderId = 1L;


    Set<Long> orderIds;
    Pageable pageable;
    Page<Order> page;

    public OrderServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.orderService = new OrderService(orderRepo,
                orderFactory,
                confirmationFactory,
                orderStatusService,
                incomeSpecificationFactory,
                orderSpecificationFactory,
                specificationBuilder);

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        
        order = new Order();
        order.setId(orderId);
        order.setDateStart(date);
        order.setTimeStart(time);

        orderIds = Set.of(order.getId());
        pageable = PageRequest.of(0, 5);
        page = new PageImpl<>(List.of(order));
    }

    @Test
    @DisplayName("Create order with correct data")
    void addOrder_WithCorrectData() {
        Mockito
                .doReturn(order)
                .when(orderRepo)
                .save(order);

        Mockito.when(orderRepo.save(order)).thenReturn(order);
        Long orderId = orderService.create(order).getId();
        Mockito.verify(orderRepo, Mockito.times(1)).save(order);
        Assertions.assertEquals(order.getId(), orderId);
    }


    @Test
    @DisplayName("Find order with correct return data")
    void getOrder_WithCorrectReturnData() {
        Mockito
                .doReturn(Optional.ofNullable(order))
                .when(orderRepo)
                .findById(orderId);
        Order createdOrder = orderRepo.findById(orderId).get();
        Mockito.verify(orderRepo, Mockito.times(1)).findById(orderId);
        Assertions.assertEquals(createdOrder.getId(), order.getId());
    }


    @Test
    @DisplayName("Find all orders in page")
    void getAllOrdersInPage() {
        Mockito
                .doReturn(page)
                .when(orderRepo)
                .findAll(pageable);
        Mockito.when(orderRepo.findAll(pageable)).thenReturn(page);
        Page<Order> serviceTypes = orderService.getAll(pageable);
        Mockito.verify(orderRepo, Mockito.times(1)).findAll(pageable);
        Assertions.assertEquals(page, serviceTypes);
    }


    @Test
    @DisplayName("Find orders with EntityNotFoundException")
    void getOrders_WithNotFoundReturnValue() {
        Mockito
                .doReturn(Optional.empty())
                .when(orderRepo)
                .findById(orderId);
        Assertions.assertNotNull(
                Assertions
                        .assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(orderId))
        );
        Mockito.verify(orderRepo, Mockito.times(1)).findById(orderId);
    }


}

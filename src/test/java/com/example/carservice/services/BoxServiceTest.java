package com.example.carservice.services;

import com.example.carservice.entities.Box;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.repos.BoxRepo;
import com.example.carservice.repos.OrderRepo;
import com.example.carservice.specification.impl.CommonSpecificationBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Optional;


class BoxServiceTest {
    @Mock
    private BoxRepo boxRepo;
    private OrderRepo orderRepo;
    private CommonSpecificationBuilder commonSpecificationBuilder;

    private final BoxService boxService;
    private final Long firstBoxId = 1L;
    private final String firstBoxName = "Box 1";
    private final Box firstBox = new Box();

    public BoxServiceTest() {
        MockitoAnnotations.openMocks(this);
        boxService = new BoxService(boxRepo, orderRepo, commonSpecificationBuilder);
        firstBox.setId(firstBoxId);
        firstBox.setName(firstBoxName);
        firstBox.setStartWorkTime(LocalTime.now());
        firstBox.setEndWorkTime(LocalTime.now().plusHours(8));
        firstBox.setTimeFactor(2.0F);
    }

    @Test
    @DisplayName("Add box to car-service")
    void addBox_WithCorrectData() {
        Mockito
                .doReturn(firstBox)
                .when(boxRepo)
                .save(firstBox);

        Box box = boxService.add(firstBox);
        Mockito.verify(boxRepo, Mockito.times(1)).save(firstBox);
        Assertions.assertEquals(box.getId(), firstBoxId);
        Assertions.assertEquals(box.getName(), firstBoxName);
    }

    @Test
    @DisplayName("Find box by non-existing id")
    void findById_EntityNotFoundException() {
        Mockito
                .doReturn(Optional.empty())
                .when(boxRepo)
                .findById(firstBoxId);
        Assertions.assertNotNull(
                Assertions
                        .assertThrows(
                                EntityNotFoundException.class, () -> boxService.getBoxById(firstBoxId))
        );
        Mockito.verify(boxRepo, Mockito.times(1)).findById(firstBoxId);
    }

    @Test
    @DisplayName("Find box by existing id")
    void findById_correctResult() {
        Mockito
                .doReturn(Optional.of(firstBox))
                .when(boxRepo)
                .findById(firstBoxId);
        Box box = boxService.getBoxById(firstBoxId);
        Mockito.verify(boxRepo, Mockito.times(1)).findById(firstBoxId);
        Assertions.assertEquals(box.getId(), firstBox.getId());
        Assertions.assertEquals(box.getName(), firstBoxName);
    }
}
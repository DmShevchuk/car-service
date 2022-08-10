package com.example.carservice.services;

import com.example.carservice.entities.Box;
import com.example.carservice.exceptions.BoxAlreadyExistsException;
import com.example.carservice.exceptions.EntityNotFoundException;
import com.example.carservice.exceptions.UnableToFindFreeBoxException;
import com.example.carservice.repos.BoxRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Класс для работы с объектами {@link Box}
 * */
@Service
@RequiredArgsConstructor
public class BoxService {

    private final BoxRepo boxRepo;


    /**
     * Регистрация в системе нового бокса <br/>
     * При этом происходит проверка на уникальность имени бокса <br/>
     * В случае, если бокс работает 24/7, то время работы устанавливается как 00:00 - 00:00
     * */
    @Transactional
    public Box add(Box box) {
        if (boxRepo.existsByName(box.getName())) {
            throw new BoxAlreadyExistsException(box.getName());
        }
        if (box.getTwentyFourHour()) {
            box.setStartWorkTime(LocalTime.of(0, 0));
            box.setEndWorkTime(LocalTime.of(0, 0));
        }
        return boxRepo.save(box);
    }


    /**
     * Поиск бокса по id
     * */
    public Box getBoxById(Long id) {
        return boxRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Box", id));
    }


    /**
     * Получение всех боксов
     * */
    public Page<Box> getAll(Pageable pageable) {
        return boxRepo.findAll(pageable);
    }


    /**
     * Поиск самого лучшего бокса для нового заказа
     * */
    public Box getBestBoxForOrder(LocalTime time, LocalDate date, LocalTime basicDuration) {
        return boxRepo.findBestBoxForOrder(time.getHour(),
                        time.getMinute(),
                        date.getYear(),
                        date.getMonthValue(),
                        date.getDayOfMonth(),
                        basicDuration.getMinute())
                .orElseThrow(() -> new UnableToFindFreeBoxException(time, date));
    }
}

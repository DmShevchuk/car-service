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

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BoxService {
    private final BoxRepo boxRepo;

    @Transactional
    public Box add(Box box) {
        if (boxRepo.existsByName(box.getName())) {
            throw new BoxAlreadyExistsException(box.getName());
        }
        return boxRepo.save(box);
    }

    @Transactional
    public Box update(Long id, Box box) {
        box.setId(id);
        return boxRepo.save(box);
    }

    public Box getBoxById(Long id) {
        return boxRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Box", id));
    }

    public Page<Box> getAll(Pageable pageable) {
        return boxRepo.findAll(pageable);
    }

    @Transactional
    public void remove(Long id) {
        boxRepo.deleteById(id);
    }

    public Box getBestBoxForOrder(LocalTime time, Date date, LocalTime basicDuration) throws ParseException {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.of("Europe/Moscow"))
                .toLocalDate();
        return boxRepo.findBestBoxForOrder(time.getHour(), time.getMinute(),
                        localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(),
                        basicDuration.getMinute())
                .orElseThrow(() -> new UnableToFindFreeBoxException(time, date));
    }
}

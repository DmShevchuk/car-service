package com.example.carservice.dto.box;

import com.example.carservice.entities.Box;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class BoxDTO {
    private Long id;
    private String name;
    private LocalTime startWorkTime;
    private LocalTime endWorkTime;
    private Float timeFactor;
    private Boolean twentyFourHour;

    public static BoxDTO toDTO(Box box){
        BoxDTO boxDTO = new BoxDTO();
        boxDTO.setId(box.getId());
        boxDTO.setName(box.getName());
        boxDTO.setStartWorkTime(box.getStartWorkTime());
        boxDTO.setEndWorkTime(box.getEndWorkTime());
        boxDTO.setTimeFactor(box.getTimeFactor());
        boxDTO.setTwentyFourHour(box.getTwentyFourHour());
        return boxDTO;
    }
}

package com.liveguard.mapper;

import com.liveguard.domain.Day;
import com.liveguard.dto.DayDTO;

public class DayMapper {

    public static DayDTO taskDayToTaskDayDTO(Day taskDay) {
        DayDTO taskDayDTO = new DayDTO(taskDay.getId(), taskDay.getDay());

        return taskDayDTO;
    }

    public static Day taskDayDTOToTaskDay(DayDTO taskDayDTO) {
        Day taskDay = new Day();
        taskDay.setDay(taskDayDTO.getDay());

        return taskDay;
    }
}

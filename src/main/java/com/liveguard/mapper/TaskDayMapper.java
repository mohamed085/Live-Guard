package com.liveguard.mapper;

import com.liveguard.domain.TaskDay;
import com.liveguard.dto.TaskDayDTO;

public class TaskDayMapper {

    public static TaskDayDTO taskDayToTaskDayDTO(TaskDay taskDay) {
        TaskDayDTO taskDayDTO = new TaskDayDTO(taskDay.getId(), taskDay.getDay());

        return taskDayDTO;
    }

    public static TaskDay taskDayDTOToTaskDay(TaskDayDTO taskDayDTO) {
        TaskDay taskDay = new TaskDay();
        taskDay.setDay(taskDayDTO.getDay());

        return taskDay;
    }
}

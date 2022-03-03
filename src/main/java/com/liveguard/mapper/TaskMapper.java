package com.liveguard.mapper;

import com.liveguard.domain.Task;
import com.liveguard.domain.TaskDay;
import com.liveguard.dto.ChipSimpleDataDTO;
import com.liveguard.dto.TaskDTO;
import com.liveguard.dto.TaskDayDTO;
import com.liveguard.dto.UserSimpleDataDTO;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskMapper {

    public static Task taskDTOToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setRingtone(taskDTO.getRingtone());
        task.setLat(taskDTO.getLat());
        task.setLng(taskDTO.getLng());
        task.setArea(taskDTO.getArea());
        task.setStartDate(taskDTO.getStartDate());
        task.setEndDate(taskDTO.getEndDate());

        return task;
    }

    public static TaskDTO taskToTaskDTO(Task task) {
        List<TaskDayDTO> repeat = new ArrayList<>();
        task.getRepeat().forEach(taskDay -> repeat.add(TaskDayMapper.taskDayToTaskDayDTO(taskDay)));

        ChipSimpleDataDTO chip = new ChipSimpleDataDTO(
                task.getChip().getId(),
                task.getChip().getName(),
                null,
                task.getChip().getUsed(),
                task.getChip().getPhoto());

        UserSimpleDataDTO addByUser = new UserSimpleDataDTO(
                task.getAddByUser().getId(),
                task.getAddByUser().getEmail(),
                task.getAddByUser().getName(),
                task.getAddByUser().getAvatar());

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setRingtone(task.getRingtone());
        taskDTO.setLat(task.getLat());
        taskDTO.setLng(task.getLng());
        taskDTO.setArea(task.getArea());
        taskDTO.setStartDate(task.getStartDate());
        taskDTO.setEndDate(task.getEndDate());
        taskDTO.setCreateDate(task.getCreateDate());
        taskDTO.setRepeat(repeat);
        taskDTO.setChip(chip);
        taskDTO.setAddByUser(addByUser);

        return taskDTO;
    }
}

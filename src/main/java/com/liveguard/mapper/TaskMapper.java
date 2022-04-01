package com.liveguard.mapper;

import com.liveguard.domain.Day;
import com.liveguard.domain.Task;
import com.liveguard.dto.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class TaskMapper {

    public static Task taskDTOToTask(TaskDTO taskDTO) {
        log.debug("TaskMapper | taskDTOToTask | taskDTO: " + taskDTO.toString());
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setRingtone(taskDTO.getRingtone());
        task.setLat(taskDTO.getLat());
        task.setLng(taskDTO.getLng());
        task.setArea(taskDTO.getArea());
        task.setStartTime(taskDTO.getStartTime());
        task.setEndTime(taskDTO.getEndTime());

        return task;
    }

    public static TaskDTO taskToTaskDTO(Task task, Boolean mute) {

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setRingtone(task.getRingtone());
        taskDTO.setLat(task.getLat());
        taskDTO.setLng(task.getLng());
        taskDTO.setArea(task.getArea());
        taskDTO.setMute(mute);
        taskDTO.setStartTime(task.getStartTime());
        taskDTO.setEndTime(task.getEndTime());
        taskDTO.setCreateDate(task.getCreateDate());
        taskDTO.setRepeat(task.getRepeat());
        taskDTO.setChip(new SimpleChipDTO(task.getChip().getId(), task.getChip().getName(), task.getChip().getPhoto()));
        taskDTO.setAddByUser(new SimpleUserDTO(task.getAddByUser().getId(), task.getAddByUser().getEmail(), task.getAddByUser().getName(), task.getAddByUser().getAbout(), task.getAddByUser().getAvatar()));

        return taskDTO;
    }

    public static TaskDTO iTaskToTaskDTO(ITaskDTO iTaskDTO, List<ITaskRepeat> repeats) {
        TaskDTO taskDTO = new TaskDTO();

        Set<Day> days = new HashSet<>();
        repeats.forEach(iTaskRepeat -> {
            days.add(new Day(iTaskRepeat.getId(), iTaskRepeat.getDay()));
        });

        taskDTO.setId(iTaskDTO.getId());
        taskDTO.setName(iTaskDTO.getName());
        taskDTO.setDescription(iTaskDTO.getDescription());
        taskDTO.setRingtone(iTaskDTO.getRingtone());
        taskDTO.setLat(iTaskDTO.getLat());
        taskDTO.setLng(iTaskDTO.getLng());
        taskDTO.setArea(iTaskDTO.getArea());
        taskDTO.setMute(iTaskDTO.getStatus());
        taskDTO.setStartTime(iTaskDTO.getStart_time());
        taskDTO.setEndTime(iTaskDTO.getEnd_time());
        taskDTO.setCreateDate(iTaskDTO.getCreate_date());
        taskDTO.setRepeat(days);
        taskDTO.setChip(new SimpleChipDTO(iTaskDTO.getChip_id(), iTaskDTO.getChip_name(), iTaskDTO.getPhoto()));
        taskDTO.setAddByUser(new SimpleUserDTO(iTaskDTO.getAdd_by_user_id(), iTaskDTO.getEmail(), iTaskDTO.getUser_name(), iTaskDTO.getAbout(), iTaskDTO.getAvatar()));

        return taskDTO;
    }

    public static SimpleTaskDTO iTaskToSimpleTaskDTO(ITaskDTO iTaskDTO) {
        SimpleTaskDTO simpleTaskDTO = new SimpleTaskDTO();

        simpleTaskDTO.setId(iTaskDTO.getId());
        simpleTaskDTO.setName(iTaskDTO.getName());
        simpleTaskDTO.setMute(iTaskDTO.getStatus());
        simpleTaskDTO.setAddByUser(new SimpleUserDTO(iTaskDTO.getAdd_by_user_id(), iTaskDTO.getEmail(), iTaskDTO.getUser_name(), iTaskDTO.getAbout(), iTaskDTO.getAvatar()));

        return simpleTaskDTO;
    }
}

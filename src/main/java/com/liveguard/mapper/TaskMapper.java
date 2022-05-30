package com.liveguard.mapper;

import com.liveguard.domain.ChipUserTask;
import com.liveguard.domain.Task;
import com.liveguard.dto.SimpleUserDTO;
import com.liveguard.dto.TaskDTO;

public class TaskMapper {

    public static Task taskDTOToTask(TaskDTO taskDTO) {
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

    public static TaskDTO chipUserTaskToTaskDTO(ChipUserTask chipUserTask) {
        SimpleUserDTO user = new SimpleUserDTO();
        user.setId(chipUserTask.getTask().getAddByUser().getId());
        user.setEmail(chipUserTask.getTask().getAddByUser().getEmail());
        user.setName(chipUserTask.getTask().getAddByUser().getName());
        user.setAbout(chipUserTask.getTask().getAddByUser().getAbout());
        user.setAvatar(chipUserTask.getTask().getAddByUser().getAvatar());

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(chipUserTask.getTask().getId());
        taskDTO.setName(chipUserTask.getTask().getName());
        taskDTO.setDescription(chipUserTask.getTask().getDescription());
        taskDTO.setRingtone(chipUserTask.getTask().getRingtone());
        taskDTO.setLat(chipUserTask.getTask().getLat());
        taskDTO.setLng(chipUserTask.getTask().getLng());
        taskDTO.setArea(chipUserTask.getTask().getArea());
        taskDTO.setStatus(chipUserTask.getStatus());
        taskDTO.setStartTime(chipUserTask.getTask().getStartTime());
        taskDTO.setEndTime(chipUserTask.getTask().getEndTime());
        taskDTO.setCreateDate(chipUserTask.getTask().getCreateDate());
        taskDTO.setRepeat(chipUserTask.getTask().getRepeat());
        taskDTO.setChipId(chipUserTask.getTask().getChip().getId());
        taskDTO.setAddByUser(user);

        return taskDTO;
    }

}

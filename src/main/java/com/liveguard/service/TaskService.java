package com.liveguard.service;

import com.liveguard.domain.Day;
import com.liveguard.domain.Task;
import com.liveguard.dto.ITaskDTO;
import com.liveguard.dto.SimpleTaskDTO;
import com.liveguard.dto.TaskDTO;
import com.liveguard.payload.ApiResponse;

import java.util.List;

public interface TaskService {

    void addTask(Long chipId, TaskDTO taskDTO);

    Task findById(Long id);

    ITaskDTO findByIdInITaskDTO(Long id);

    TaskDTO findByIdForUser(Long id);

    List<SimpleTaskDTO> findAllInChip(Long id);

    List<SimpleTaskDTO> findMyTasksInChip(Long id);

    List<SimpleTaskDTO> findOtherTasksInChip(Long id);

    List<Task> findByChipIdAndRepeatEquals(Long chipId, Day day);
}

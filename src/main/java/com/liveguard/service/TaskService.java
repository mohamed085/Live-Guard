package com.liveguard.service;

import com.liveguard.domain.Task;
import com.liveguard.dto.SimpleTaskDTO;
import com.liveguard.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO addTask(Long chipId, TaskDTO taskDTO);

    TaskDTO findById(Long id);

    List<SimpleTaskDTO> findAllInChip(Long id);

    List<SimpleTaskDTO> findMyTasksInChip(Long id);

    List<SimpleTaskDTO> findOtherTasksInChip(Long id);
}

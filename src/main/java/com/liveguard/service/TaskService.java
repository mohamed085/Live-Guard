package com.liveguard.service;

import com.liveguard.domain.Task;
import com.liveguard.dto.TaskDTO;
import com.liveguard.dto.TaskSimpleDataDTO;

import java.text.ParseException;
import java.util.List;

public interface TaskService {

    Task findById(Long id);

    Task addTask(Long chipId, TaskDTO taskDTO);

    List<TaskSimpleDataDTO> findByChipId(Long id);

    List<TaskSimpleDataDTO> findByChipIdAndUser(Long id);

    List<TaskSimpleDataDTO> findByChipIdAndSpecificUser(Long userId, Long chipId);

}

package com.liveguard.service;

import com.liveguard.domain.ChipUser;
import com.liveguard.domain.ChipUserTask;
import com.liveguard.domain.Task;

import java.util.List;

public interface ChipUserTaskService {

    void addChipUserTaskForNewTask(Task task);

    void addChipUserTaskForNewChipUser(ChipUser chipUser);

    ChipUserTask findByTaskIdAndAuthenticatedUser(Long taskId);

    void deleteByTaskIdAndAuthenticatedUser(Long taskId);

    List<ChipUserTask> findAllTasksForAuthenticatedUserByChipId(Long chipId);
}

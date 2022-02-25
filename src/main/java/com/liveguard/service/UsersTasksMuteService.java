package com.liveguard.service;

import com.liveguard.domain.Chip;
import com.liveguard.domain.Task;
import com.liveguard.domain.User;
import com.liveguard.domain.UsersTasksMute;

import java.util.List;

public interface UsersTasksMuteService {

    void addTasksMuteDefaultToNewTask(Chip chip, Task task);

    void addUsersMuteDefaultToAddNewChipToUser(Chip chip, User user);

    UsersTasksMute findByTaskIdAndUserId(Long taskId, Long userId);

    void updateMuteStatusByUserIdAndTaskId(Long userId, Long taskId, Boolean status);
}

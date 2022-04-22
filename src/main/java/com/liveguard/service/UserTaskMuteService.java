package com.liveguard.service;

import com.liveguard.domain.Chip;
import com.liveguard.domain.Task;
import com.liveguard.domain.User;
import com.liveguard.domain.UserTaskMute;
import com.liveguard.payload.ApiResponse;

public interface UserTaskMuteService {

    void addTasksMuteDefaultToNewUser(Long chipId, User user);

    void addTasksMuteDefaultToNewTask(Task task, Chip chip);

    void updateMuteStatus(Long taskId, Boolean mute);

    void deleteUserTaskMute(Long userId, Long chipId);
}

package com.liveguard.service;

import com.liveguard.domain.Chip;
import com.liveguard.domain.Task;
import com.liveguard.domain.User;
import com.liveguard.domain.UserTaskMute;

public interface UserTaskMuteService {

    void addTasksMuteDefaultToNewUser(Long chipId, User user);

    void addTasksMuteDefaultToNewTask(Task task, Chip chip);

}

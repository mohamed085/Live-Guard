package com.liveguard.service;

import com.liveguard.domain.TaskDay;

import java.util.List;

public interface TaskDayService {

    List<TaskDay> findAll();

    TaskDay findById(Long id);

}

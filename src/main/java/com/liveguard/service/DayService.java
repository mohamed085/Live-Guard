package com.liveguard.service;

import com.liveguard.domain.Day;

import java.util.List;

public interface DayService {

    List<Day> findAll();

    Day findById(Long id);

}

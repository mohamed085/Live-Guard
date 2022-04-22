package com.liveguard.service;

import com.liveguard.domain.Day;
import com.liveguard.domain.EnumDay;

import java.util.List;

public interface DayService {

    List<Day> findAll();

    Day findById(Long id);

    Day findByDay(EnumDay enumDay);
}

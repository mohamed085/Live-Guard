package com.liveguard.repository;

import com.liveguard.domain.Day;
import com.liveguard.domain.EnumDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

    Optional<Day> findByDay(EnumDay day);
}

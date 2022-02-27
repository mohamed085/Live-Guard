package com.liveguard.repository;

import com.liveguard.domain.Day;
import com.liveguard.domain.TaskDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDayRepository extends JpaRepository<TaskDay, Long> {

    TaskDay findByDay(Day day);
}

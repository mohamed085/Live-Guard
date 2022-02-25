package com.liveguard.repository;

import com.liveguard.domain.TaskDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDayRepository extends CrudRepository<TaskDay, Long> {
}

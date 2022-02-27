package com.liveguard.repository;

import com.liveguard.domain.Task;
import com.liveguard.domain.TaskDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByChipId(Long id);

    List<Task> findByChipIdAndAddByUserId(Long chipId, Long userId);

    List<Task> findByChipIdAndRepeatEquals(Long chip_id, TaskDay repeat);

}

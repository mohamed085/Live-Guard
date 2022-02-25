package com.liveguard.repository;

import com.liveguard.domain.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByChipId(Long id);

    List<Task> findByChipIdAndAddByUserId(Long chipId, Long userId);

}

package com.liveguard.repository;

import com.liveguard.domain.ChipUserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChipUserTaskRepository extends JpaRepository<ChipUserTask, Long> {

    @Query("SELECT cut FROM ChipUserTask cut WHERE cut.task.id = ?1 AND cut.chipUser.user.id = ?2")
    Optional<ChipUserTask> findByTaskIdAndUserId(Long taskId, Long userId);

    @Query("SELECT cut FROM ChipUserTask cut WHERE cut.chipUser.user.id = ?2 AND cut.chipUser.chip.id = ?1")
    List<ChipUserTask> findAllByChipAndUser(Long chipId, Long userId);
}

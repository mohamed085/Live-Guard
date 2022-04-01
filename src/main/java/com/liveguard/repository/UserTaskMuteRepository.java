package com.liveguard.repository;

import com.liveguard.domain.UserTaskMute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTaskMuteRepository extends JpaRepository<UserTaskMute, Long> {

    UserTaskMute findByTaskIdAndUserId(Long taskId, Long userId);

    @Query("UPDATE UserTaskMute t SET t.status = ?3 WHERE t.task.id = ?2 AND t.user.id = ?1")
    @Modifying
    void updateMuteStatus(Long userId, Long taskId, Boolean status);
}

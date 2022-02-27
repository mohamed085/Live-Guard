package com.liveguard.repository;

import com.liveguard.domain.UsersTasksMute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersTasksMuteRepository extends JpaRepository<UsersTasksMute, Long> {

    UsersTasksMute findByTaskIdAndUserId(Long taskId, Long userId);

    @Query("UPDATE UsersTasksMute t SET t.status = ?3 WHERE t.task.id = ?2 AND t.user.id = ?1")
    @Modifying
    void updateMuteStatus(Long userId, Long taskId, Boolean status);
}

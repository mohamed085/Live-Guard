package com.liveguard.repository;

import com.liveguard.domain.Email;
import com.liveguard.domain.EmailSendStatus;
import com.liveguard.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EmailRepository extends PagingAndSortingRepository<Email, Long> {

    @Query("UPDATE Email e SET e.status = ?2, e.sendDate = ?3 WHERE e.id = ?1")
    @Modifying
    void updateStatus(Long id, EmailSendStatus status, LocalDateTime sendDate);
}

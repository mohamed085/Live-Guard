package com.liveguard.repository;

import com.liveguard.domain.EmailSendStatus;
import com.liveguard.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    @Query("UPDATE VerificationCode code SET code.emailSendStatus = ?2 WHERE code.id = ?1")
    @Modifying
    void updateVerificationCodeStatus(Long codeId, EmailSendStatus status);

    Optional<VerificationCode> findByCode(String code);
}

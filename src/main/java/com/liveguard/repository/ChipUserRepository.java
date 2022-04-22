package com.liveguard.repository;

import com.liveguard.domain.ChipUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChipUserRepository extends JpaRepository<ChipUser, Long> {

    Optional<ChipUser> findByChipIdAndUserId(Long chipId, Long userId);

    Boolean existsByChipIdAndUserId(Long chipId, Long userId);

    List<ChipUser> findAllByUserId(Long id);

    @Query("DELETE from ChipUser cu WHERE cu.chip.id = ?1 AND cu.user.id = ?2")
    @Modifying
    void deleteByChipIdAndUserId(Long chipId, Long userId);
}

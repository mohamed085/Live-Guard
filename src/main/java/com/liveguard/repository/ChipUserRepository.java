package com.liveguard.repository;

import com.liveguard.domain.ChipUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChipUserRepository extends JpaRepository<ChipUser, Long> {

    Optional<ChipUser> findByChipIdAndUserId(Long chipId, Long userId);

    Boolean existsByChipIdAndUserId(Long chipId, Long userId);

    List<ChipUser> findAllByUserId(Long id);
}

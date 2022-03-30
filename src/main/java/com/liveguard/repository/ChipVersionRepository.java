package com.liveguard.repository;

import com.liveguard.domain.ChipVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChipVersionRepository extends JpaRepository<ChipVersion, Long> {

    List<ChipVersion> findAllByEnabledTrue();
}

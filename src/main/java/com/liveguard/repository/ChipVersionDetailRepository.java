package com.liveguard.repository;

import com.liveguard.domain.ChipVersionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChipVersionDetailRepository extends JpaRepository<ChipVersionDetail, Long> {

    void deleteAllByChipVersionId(Long id);
}

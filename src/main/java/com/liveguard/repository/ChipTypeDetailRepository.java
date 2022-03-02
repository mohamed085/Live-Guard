package com.liveguard.repository;

import com.liveguard.domain.ChipTypeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChipTypeDetailRepository extends JpaRepository<ChipTypeDetail, Long> {

    void deleteAllByChipTypeId(Long id);
}

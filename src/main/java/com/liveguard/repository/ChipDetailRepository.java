package com.liveguard.repository;

import com.liveguard.domain.ChipDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChipDetailRepository extends JpaRepository<ChipDetail, Long> {

    void deleteAllByChipId(Long id);
}

package com.liveguard.repository;

import com.liveguard.domain.ChipUserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChipUserDetailRepository extends JpaRepository<ChipUserDetail, Long> {
}

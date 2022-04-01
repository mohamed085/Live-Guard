package com.liveguard.repository;

import com.liveguard.domain.ChipVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChipVersionRepository extends JpaRepository<ChipVersion, Long> {

    List<ChipVersion> findAllByEnabledTrue();

    @Query("UPDATE ChipVersion c SET c.enabled = ?2 WHERE c.id = ?1")
    @Modifying
    void updateEnabledStatus(Long id, Boolean status);

    @Query("UPDATE ChipVersion c SET c.inStock = ?2 WHERE c.id = ?1")
    @Modifying
    void updateInStockStatus(Long id, Boolean status);
}

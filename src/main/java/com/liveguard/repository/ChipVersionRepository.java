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

    @Query("UPDATE ChipVersion c SET c.mainImage = ?2 WHERE c.id = ?1")
    @Modifying
    void updateMainImage(Long id, String image);

    @Query("UPDATE ChipVersion c SET c.shortDescription = ?2, c.fullDescription = ?3 WHERE c.id = ?1")
    @Modifying
    void updateDescription(Long id, String shortDescription, String fullDescription);

    @Query("UPDATE ChipVersion c SET c.weight = ?2 WHERE c.id = ?1")
    @Modifying
    void updateShipping(Long id, String shortDescription, Double weight);

}

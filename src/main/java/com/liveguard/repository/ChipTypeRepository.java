package com.liveguard.repository;

import com.liveguard.domain.ChipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface ChipTypeRepository extends JpaRepository<ChipType, Long> {

    @Query("UPDATE ChipType c SET c.enabled = ?2 WHERE c.id = ?1")
    @Modifying
    void updateEnabledStatus(Long id, Boolean status);

    @Query("UPDATE ChipType c SET c.inStock = ?2 WHERE c.id = ?1")
    @Modifying
    void updateInStockStatus(Long id, Boolean status);


}

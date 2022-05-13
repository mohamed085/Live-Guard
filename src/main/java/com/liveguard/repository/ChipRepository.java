package com.liveguard.repository;

import com.liveguard.domain.Chip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChipRepository extends PagingAndSortingRepository<Chip, Long> {

    boolean existsByKey(String key);

    @Query("SELECT c FROM Chip c WHERE c.key LIKE %?1% OR c.password LIKE %?1% OR c.chipVersion.name LIKE %?1%")
    Page<Chip> findAll(String keyword, Pageable pageable);

    List<Chip> findAllByChipVersionId(Long id);

    @Query("UPDATE Chip c SET c.used = ?2 WHERE c.id = ?1")
    @Modifying
    void updateUsedStatus(Long id, Boolean used);

    boolean existsByIdAndUsed(Long id, Boolean used);
}

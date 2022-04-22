package com.liveguard.repository;

import com.liveguard.domain.Chip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChipRepository extends PagingAndSortingRepository<Chip, Long> {

    @Query("SELECT c FROM Chip c WHERE c.name LIKE %?1% OR c.password LIKE %?1% OR c.chipVersion.name LIKE %?1%")
    Page<Chip> findAll(String keyword, Pageable pageable);

    List<Chip> findAllByChipVersionId(Long id);

    @Query("UPDATE Chip c SET c.photo = ?2 WHERE c.id = ?1")
    @Modifying
    void updatePhoto(Long id, String photo);

    @Query("UPDATE Chip c SET c.name = ?2 WHERE c.id = ?1")
    @Modifying
    void updateName(Long id, String name);

    @Query("UPDATE Chip c SET c.used = ?2 WHERE c.id = ?1")
    @Modifying
    void updateUsedStatus(Long id, Boolean used);
}

package com.liveguard.repository;

import com.liveguard.domain.Chip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChipRepository extends JpaRepository<Chip, Long> {

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

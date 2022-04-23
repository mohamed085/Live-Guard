package com.liveguard.repository;

import com.liveguard.domain.Location;
import com.liveguard.domain.LocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("UPDATE Location l SET l.status = ?2 WHERE l.id = ?1")
    @Modifying
    void updateStatus(Long id, LocationStatus status);
}

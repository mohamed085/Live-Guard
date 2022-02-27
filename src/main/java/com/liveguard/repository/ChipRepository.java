package com.liveguard.repository;

import com.liveguard.domain.Chip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface ChipRepository extends JpaRepository<Chip, Long> {

    List<Chip> findByChipTypeId(Long chipTypeId);
}

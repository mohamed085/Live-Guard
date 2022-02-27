package com.liveguard.repository;

import com.liveguard.domain.ChipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface ChipTypeRepository extends JpaRepository<ChipType, Long> {
}

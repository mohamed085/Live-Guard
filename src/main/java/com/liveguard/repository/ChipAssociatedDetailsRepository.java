package com.liveguard.repository;

import com.liveguard.domain.ChipAssociatedDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChipAssociatedDetailsRepository extends CrudRepository<ChipAssociatedDetails, Long> {
}

package com.liveguard.repository;

import com.liveguard.domain.Notification;
import com.liveguard.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

    Page<Review> findAllByChipVersionId(Long chipVersionId, Pageable pageable);
}

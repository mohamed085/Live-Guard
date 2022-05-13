package com.liveguard.service;

import com.liveguard.domain.Review;
import com.liveguard.dto.ReviewDTO;
import org.springframework.data.domain.Page;

public interface ReviewServices {

    Page<Review> findAllByChipVersionId(Long id, int pageNum);
    void save(ReviewDTO reviewDTO);
}

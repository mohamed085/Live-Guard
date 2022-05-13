package com.liveguard.service.serviceImp;

import com.liveguard.domain.ChipVersion;
import com.liveguard.domain.Review;
import com.liveguard.domain.User;
import com.liveguard.dto.ReviewDTO;
import com.liveguard.mapper.ReviewMapper;
import com.liveguard.repository.ReviewRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.ChipVersionService;
import com.liveguard.service.ReviewServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ReviewServicesImp implements ReviewServices {

    public static final int REVIEWS_PER_PAGE = 20;

    private final ReviewRepository reviewRepository;
    private final ChipVersionService chipVersionService;
    private final AccountService accountService;

    public ReviewServicesImp(ReviewRepository reviewRepository, ChipVersionService chipVersionService, AccountService accountService) {
        this.reviewRepository = reviewRepository;
        this.chipVersionService = chipVersionService;
        this.accountService = accountService;
    }

    @Override
    public Page<Review> findAllByChipVersionId(Long id, int pageNum) {
        log.debug("ReviewServices | reviews | reviewDTO: " + id);
        log.debug("ReviewServices | reviews | pageNum: " + pageNum);

        Pageable pageable = PageRequest.of(pageNum - 1 , REVIEWS_PER_PAGE, Sort.by("reviewTime").descending());

        return reviewRepository.findAllByChipVersionId(id, pageable);
    }

    @Override
    public void save(ReviewDTO reviewDTO) {
        log.debug("ReviewServices | save | reviewDTO: " + reviewDTO.toString());

        User user = accountService.getAuthenticatedAccount();
        log.debug("ReviewServices | save | user: " + user.toString());

        ChipVersion chipVersion = chipVersionService.findById(reviewDTO.getChipVersionId());
        log.debug("ReviewServices | save | chipVersion: " + chipVersion.toString());

        Review review = ReviewMapper.reviewDTOToReview(reviewDTO);
        review.setReviewTime(LocalDateTime.now());
        review.setChipVersion(chipVersion);
        review.setUser(user);

        reviewRepository.save(review);
        chipVersionService.updateReviewCountAndAverageRating(chipVersion.getId());
    }
}

package com.liveguard.mapper;

import com.liveguard.domain.Review;
import com.liveguard.dto.ReviewDTO;
import com.liveguard.dto.SimpleChipVersion;
import com.liveguard.dto.SimpleUserDTO;

import java.time.LocalDateTime;

public class ReviewMapper {
    
    public static Review reviewDTOToReview(ReviewDTO reviewDTO) {
        Review review = new Review();

        review.setHeadline(reviewDTO.getHeadline());
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());

        return review;
    }

    public static ReviewDTO reviewToReviewDTO(Review review) {
        SimpleUserDTO user = new SimpleUserDTO(review.getUser().getId(),
                review.getUser().getEmail(), review.getUser().getName(),
                review.getUser().getAbout(), review.getUser().getAvatar());

        SimpleChipVersion chipVersion = new SimpleChipVersion(review.getChipVersion().getId(),
                review.getChipVersion().getName(), review.getChipVersion().getMainImage(),
                review.getChipVersion().getAverageRating());

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setHeadline(review.getHeadline());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setReviewTime(review.getReviewTime());
        reviewDTO.setChipVersionId(review.getChipVersion().getId());
        reviewDTO.setUser(user);
        reviewDTO.setChipVersion(chipVersion);

        return reviewDTO;
    }
}

package com.liveguard.controller;

import com.liveguard.dto.ReviewDTO;
import com.liveguard.mapper.ReviewMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.ReviewServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewServices reviewServices;

    public ReviewController(ReviewServices reviewServices) {
        this.reviewServices = reviewServices;
    }

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody ReviewDTO reviewDTO) {
        log.debug("ReviewController | add | reviewDTO: " + reviewDTO.toString());
        reviewServices.save(reviewDTO);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Review added successfully"));
    }

    @GetMapping("/{chipVersionId}")
    public ResponseEntity<?> getFirstPage(@PathVariable("chipVersionId") Long chipVersionId) {

        return getAllByPage(chipVersionId, 1);
    }

    @GetMapping("/{chipVersionId}/page/{pageNum}")
    public ResponseEntity<?> getAllByPage(@PathVariable("chipVersionId") Long chipVersionId,
                                          @PathVariable(name = "pageNum") int pageNum) {

        log.debug("ReviewController | getAllByPage");
        log.debug("ReviewController | getAllByPage | pageNum: " + pageNum);

        Page<ReviewDTO> reviewDTOs = reviewServices.findAllByChipVersionId(chipVersionId, pageNum)
                .map(review -> {
                    ReviewDTO reviewDTO = ReviewMapper.reviewToReviewDTO(review);
                    return reviewDTO;
                });

        return ResponseEntity
                .ok()
                .body(reviewDTOs);
    }

}

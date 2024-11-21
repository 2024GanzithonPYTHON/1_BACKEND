package com.ganzithon.go_farming.review.controller;

import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.review.dto.ReviewRequestDTO;
import com.ganzithon.go_farming.review.dto.ReviewResponseDTO;
import com.ganzithon.go_farming.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review/{reviewId}") // 리뷰 상세조회
    public ResponseDTO<ReviewResponseDTO> findReviewById(@PathVariable Long reviewId) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return reviewService.findReviewById(reviewId);
    }

    @GetMapping("/place/{placeId}/review") // 특정 장소 리뷰 전체조회
    public ResponseDTO<List<ReviewResponseDTO>> findReviews(@PathVariable Long placeId) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return reviewService.findReviews(placeId);
    }

    @PostMapping(value = "/place/{placeId}/review", consumes = "multipart/form-data")
    public ResponseDTO<?> createReview(@PathVariable Long placeId,
                                       @RequestPart("review") ReviewRequestDTO dto,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> photos) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return reviewService.createReview(dto, username, placeId, photos);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseDTO<?> deleteReview(@PathVariable Long reviewId) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return reviewService.deleteReview(reviewId, username);
    }

    @PostMapping("/review/{reviewId}/like")
    public ResponseDTO<?> likeReview(@PathVariable Long reviewId) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return reviewService.likeReview(reviewId, username);
    }

    // 현재 인증된 사용자명을 가져오는 헬퍼 메서드
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName();
    }
}

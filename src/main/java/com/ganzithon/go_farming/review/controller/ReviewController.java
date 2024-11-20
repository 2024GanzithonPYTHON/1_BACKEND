package com.ganzithon.go_farming.review.controller;

import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.review.dto.ReviewRequestDTO;
import com.ganzithon.go_farming.review.dto.ReviewResponseDTO;
import com.ganzithon.go_farming.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review/{reviewId}") // 리뷰 상세조회
    public ResponseDTO<ReviewResponseDTO> findReviewById(@PathVariable Long reviewId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return reviewService.findReviewById(reviewId);
    }

    @GetMapping("/place/{placeId}/review") // 특정 장소 리뷰 전체조회
    public ResponseDTO<List<ReviewResponseDTO>> findReviews(@PathVariable Long placeId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return reviewService.findReviews(placeId);
    }

    @PostMapping(value = "/place/{placeId}/review", consumes = "multipart/form-data")
    public ResponseDTO<?> createReview(@PathVariable Long placeId,
                                       @RequestPart("review") ReviewRequestDTO dto,  // JSON 데이터 처리
                                       @RequestPart(value = "files", required = false) List<MultipartFile> photos, // 파일 처리
                                       HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return reviewService.createReview(dto, (Long) session.getAttribute("userId"), placeId, photos);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseDTO<?> deleteReview(@PathVariable Long reviewId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return reviewService.deleteReview(reviewId, (Long) session.getAttribute("userId"));
    }

    private boolean notAuthorized(HttpSession session) {
        return session == null || session.getAttribute("userId") == null;
    }

    @PostMapping("/review/{reviewId}/like")
    public ResponseDTO<?> likeReview(@PathVariable Long reviewId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return reviewService.likeReview(reviewId, (Long) session.getAttribute("userId"));
    }
}

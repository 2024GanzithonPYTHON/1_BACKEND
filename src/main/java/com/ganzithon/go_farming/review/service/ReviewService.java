package com.ganzithon.go_farming.review.service;

import com.ganzithon.go_farming.common.domain.Place;
import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.repository.KeywordRepository;
import com.ganzithon.go_farming.common.repository.PlaceRepository;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.response.Responses;
import com.ganzithon.go_farming.common.service.S3Service;
import com.ganzithon.go_farming.review.domain.*;
import com.ganzithon.go_farming.review.dto.ReviewRequestDTO;
import com.ganzithon.go_farming.review.dto.ReviewResponseDTO;
import com.ganzithon.go_farming.review.repository.LikeRepository;
import com.ganzithon.go_farming.review.repository.ReviewKeywordRepository;
import com.ganzithon.go_farming.review.repository.ReviewPhotoRepository;
import com.ganzithon.go_farming.review.repository.ReviewRepository;
import com.ganzithon.go_farming.user.User;
import com.ganzithon.go_farming.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final S3Service s3Service;
    private final KeywordRepository keywordRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final LikeRepository likeRepository;

    public ResponseDTO<List<ReviewResponseDTO>> findReviews(Long placeId) {
        List<Review> reviews = reviewRepository.findAllByPlaceId(placeId);
        List<ReviewResponseDTO> response = reviews.stream().map(ReviewResponseDTO::of).toList();
        return new ResponseDTO<>(response, Responses.OK);
    }

    public ResponseDTO<ReviewResponseDTO> findReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(Exceptions.REVIEW_NOT_EXIST));
        ReviewResponseDTO response = ReviewResponseDTO.of(review);
        return new ResponseDTO<>(response, Responses.OK);
    }

    @Transactional
    public ResponseDTO<?> createReview(ReviewRequestDTO reviewRequestDTO, String username, Long placeId,
                                       List<MultipartFile> photos) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(Exceptions.MEMBER_NOT_EXIST));
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(Exceptions.PLACE_NOT_EXIST));
        List<Review> writtenReviews = reviewRepository.findAllByPlaceIdAndUserUserId(placeId, user.getUserId());
        int visitedCount = writtenReviews.size();
        Review review = new Review(user, place, reviewRequestDTO, visitedCount + 1);

        reviewRepository.save(review);

        if (photos != null) {
            List<ReviewPhoto> reviewPhotos = photos.stream()
                    .map(photo -> {
                        String imageUrl = s3Service.uploadFile(photo, "reviews");
                        return new ReviewPhoto(review, imageUrl);
                    }).toList();
            reviewPhotoRepository.saveAll(reviewPhotos);
        }

        List<ReviewKeyword> reviewKeywords = reviewRequestDTO.getKeywords().stream().map(
                keywordId -> {
                    Keyword keyword = keywordRepository.findById(keywordId)
                            .orElseThrow(() -> new CustomException(Exceptions.KEYWORD_NOT_EXIST));
                    return new ReviewKeyword(review, keyword);
                }).toList();
        reviewKeywordRepository.saveAll(reviewKeywords);

        return new ResponseDTO<>(Responses.CREATED);
    }

    @Transactional
    public ResponseDTO<?> deleteReview(Long reviewId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(Exceptions.MEMBER_NOT_EXIST));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(Exceptions.REVIEW_NOT_EXIST));
        if (!review.getUser().equals(user)) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }
        reviewRepository.delete(review);
        return new ResponseDTO<>(Responses.NO_CONTENT);
    }

    @Transactional
    public ResponseDTO<?> likeReview(Long reviewId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(Exceptions.MEMBER_NOT_EXIST));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(Exceptions.REVIEW_NOT_EXIST));

        Optional<Like> like = likeRepository.findByUserUserIdAndReviewId(user.getUserId(), reviewId);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
            review.dislike();
        } else {
            likeRepository.save(new Like(user, review));
            review.like();
        }

        return new ResponseDTO<>(Responses.OK);
    }
}

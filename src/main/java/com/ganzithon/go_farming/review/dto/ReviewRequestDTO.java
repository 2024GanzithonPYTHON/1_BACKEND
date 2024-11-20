package com.ganzithon.go_farming.review.dto;

import com.ganzithon.go_farming.review.domain.Review;
import com.ganzithon.go_farming.review.domain.ReviewPhoto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewDTO {

    private Long id;
    private Long userId;
    private Long placeId;
    private String content;
    private List<String> photos;
    private List<QuestionDTO> questions;
    private int likeCount;
    private int visitedCount;
    private LocalDate createdAt;

    public static ReviewDTO of(Review review) {
        return new ReviewDTO(review.getId(), review.getUser().getUserId(), review.getPlace().getId(),
                review.getContent(), review.getPhotos().stream().map(ReviewPhoto::getUrl).toList(),
                review.getQuestions().stream().map(QuestionDTO::of).toList(),
                review.getLikeCount(), review.getVisitedCount(), review.getCreatedAt());
    }

}

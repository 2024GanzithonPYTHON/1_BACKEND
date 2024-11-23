package com.ganzithon.go_farming.review.dto;

import com.ganzithon.go_farming.review.domain.Question;
import com.ganzithon.go_farming.review.domain.Review;
import com.ganzithon.go_farming.review.domain.ReviewPhoto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewResponseDTO {

    private Long id;
    private String nickname;
    private Long placeId;
    private String content;
    private List<String> photos;
    private List<QuestionResponseDTO> questions;
    private int likeCount;
    private int visitedCount;
    private LocalDate createdAt;

    public static ReviewResponseDTO of(Review review) {
        return new ReviewResponseDTO(
                review.getId(), review.getUser().getNickname(),
                review.getPlace().getId(), review.getContent(),
                review.getPhotos().stream().map(ReviewPhoto::getUrl).toList(),
                review.getQuestions().stream().map(QuestionResponseDTO::of).toList(),
                review.getLikeCount(), review.getVisitedCount(),
                review.getCreatedAt()
        );
    }

}

package com.ganzithon.go_farming.review.dto;

import com.ganzithon.go_farming.review.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class QuestionResponseDTO {

    private Long id;
    private Long userId;
    private Long reviewId;
    private String content;
    private LocalDate createdAt;

    public static QuestionResponseDTO of(Question question) {
        return new QuestionResponseDTO(question.getId(), question.getUser().getUserId(), question.getReview().getId(),
                question.getContent(), question.getCreatedAt());
    }

}

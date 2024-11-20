package com.ganzithon.go_farming.review.dto;

import com.ganzithon.go_farming.review.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class QuestionDTO {

    private Long userId;
    private Long reviewId;
    private String content;
    private LocalDate createdAt;

    public static QuestionDTO of(Question question) {
        return new QuestionDTO(question.getId(), question.getReview().getId(),
                question.getContent(), question.getCreatedAt());
    }

}

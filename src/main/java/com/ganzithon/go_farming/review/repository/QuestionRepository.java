package com.ganzithon.go_farming.review.repository;

import com.ganzithon.go_farming.review.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByReviewId(Long reviewId);

}

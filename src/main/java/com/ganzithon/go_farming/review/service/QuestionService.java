package com.ganzithon.go_farming.review.service;

import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.response.Responses;
import com.ganzithon.go_farming.review.domain.Question;
import com.ganzithon.go_farming.review.domain.Review;
import com.ganzithon.go_farming.review.dto.QuestionRequestDTO;
import com.ganzithon.go_farming.review.repository.QuestionRepository;
import com.ganzithon.go_farming.review.repository.ReviewRepository;
import com.ganzithon.go_farming.user.User;
import com.ganzithon.go_farming.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ResponseDTO<?> createQuestion(QuestionRequestDTO questionRequestDTO, Long reviewId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(Exceptions.MEMBER_NOT_EXIST));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(Exceptions.REVIEW_NOT_EXIST));

        Question question = new Question(user, review, questionRequestDTO);
        questionRepository.save(question);
        return new ResponseDTO<>(Responses.CREATED);
    }

    @Transactional
    public ResponseDTO<?> deleteQuestion(Long questionId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(Exceptions.MEMBER_NOT_EXIST));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(Exceptions.QUESTION_NOT_EXIST));
        if (!question.getUser().equals(user)) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        questionRepository.delete(question);
        return new ResponseDTO<>(Responses.NO_CONTENT);
    }
}

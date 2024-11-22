package com.ganzithon.go_farming.review.controller;

import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.review.dto.QuestionRequestDTO;
import com.ganzithon.go_farming.review.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/review/{reviewId}/question")
    public ResponseDTO<?> createQuestion(@RequestBody QuestionRequestDTO dto, @PathVariable Long reviewId) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return questionService.createQuestion(dto, reviewId, username);
    }

    @DeleteMapping("/question/{questionId}")
    public ResponseDTO<?> deleteQuestion(@PathVariable Long questionId) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return questionService.deleteQuestion(questionId, username);
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

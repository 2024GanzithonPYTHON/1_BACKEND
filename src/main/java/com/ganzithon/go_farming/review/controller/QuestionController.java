package com.ganzithon.go_farming.review.controller;

import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.review.dto.QuestionRequestDTO;
import com.ganzithon.go_farming.review.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/review/{reviewId}/question")
    public ResponseDTO<?> createQuesetion(@RequestBody QuestionRequestDTO dto,
                                          @PathVariable Long reviewId,
                                          HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return questionService.createQuestion(dto, reviewId, (Long) session.getAttribute("userId"));
    }

    @DeleteMapping("/question/{questionId}")
    public ResponseDTO<?> deleteQuestion(@PathVariable Long questionId,
                                         HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return questionService.deleteQuestion(questionId, (Long) session.getAttribute("userId"));
    }

    private boolean notAuthorized(HttpSession session) {
        return session == null || session.getAttribute("userId") == null;
    }
}

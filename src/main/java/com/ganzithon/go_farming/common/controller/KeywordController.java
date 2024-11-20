package com.ganzithon.go_farming.common.controller;

import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.service.KeywordService;
import com.ganzithon.go_farming.review.dto.KeywordDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private KeywordService keywordService;

    @GetMapping("/keyword/{categoryId}")
    public ResponseDTO<List<KeywordDTO>> findKeywordsByCategory(@PathVariable Long categoryId,
                                                                HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return keywordService.findKeywordsByCategory(categoryId);
    }

    private boolean notAuthorized(HttpSession session) {
        return session == null || session.getAttribute("userId") == null;
    }
}

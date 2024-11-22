package com.ganzithon.go_farming.common.controller;

import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.service.KeywordService;
import com.ganzithon.go_farming.review.dto.KeywordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("/keyword/{categoryId}")
    public ResponseDTO<List<KeywordDTO>> findKeywordsByCategory(@PathVariable Long categoryId) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return keywordService.findKeywordsByCategory(categoryId);
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

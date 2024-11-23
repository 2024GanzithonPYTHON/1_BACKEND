package com.ganzithon.go_farming.common.controller;

import com.ganzithon.go_farming.common.dto.PlaceRequestDTO;
import com.ganzithon.go_farming.common.dto.PlaceResponseDTO;
import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.service.PlaceService;
import com.ganzithon.go_farming.review.dto.PlaceResponseWithCountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/kakao") // 카카오맵 아이디로 장소 조회 및 생성
    public ResponseDTO<PlaceResponseDTO> findPlaceWithKakao(@RequestBody PlaceRequestDTO dto) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return placeService.findPlaceByKakaoId(dto, username);
    }

    @GetMapping
    public ResponseDTO<PlaceResponseWithCountDTO> findPlacesByCondition(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long provinceId,
            @RequestParam(required = false) Long cityId) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return placeService.findPlaceByCondition(categoryId, provinceId, cityId, username);
    }

    @GetMapping("/{placeId}")
    public ResponseDTO<PlaceResponseDTO> findPlaceById(@PathVariable Long placeId) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return placeService.findPlaceById(placeId, username);
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

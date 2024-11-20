package com.ganzithon.go_farming.common.controller;

import com.ganzithon.go_farming.common.dto.PlaceRequestDTO;
import com.ganzithon.go_farming.common.dto.PlaceResponseDTO;
import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.service.PlaceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/kakao") // 카카오맵 아이디로 장소 조회 및 생성
    public ResponseDTO<PlaceResponseDTO> findPlaceWithKakao(@RequestBody PlaceRequestDTO dto,
                                                            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return placeService.findPlaceByKakaoId(dto);
    }

    @GetMapping
    public ResponseDTO<List<PlaceResponseDTO>> findPlacesByCondition(@RequestParam(required = false) Long categoryId,
                                                                     @RequestParam(required = false) Long provinceId,
                                                                     @RequestParam(required = false) Long cityId,
                                                                     HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return placeService.findPlaceByCondition(categoryId, provinceId, cityId);
    }

    @GetMapping("/{placeId}")
    public ResponseDTO<PlaceResponseDTO> findPlaceById(@PathVariable Long placeId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (notAuthorized(session)) {throw new CustomException(Exceptions.UNAUTHORIZED);}

        return placeService.findPlaceById(placeId);
    }

    private boolean notAuthorized(HttpSession session) {
        return session == null || session.getAttribute("userId") == null;
    }

}

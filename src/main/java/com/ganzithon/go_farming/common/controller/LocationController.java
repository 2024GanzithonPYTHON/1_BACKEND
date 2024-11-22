package com.ganzithon.go_farming.common.controller;

import com.ganzithon.go_farming.common.dto.CityDTO;
import com.ganzithon.go_farming.common.dto.ProvinceDTO;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.service.LocationService;
import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/province")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("")
    public ResponseDTO<List<ProvinceDTO>> findProvincesAll() {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return locationService.getProvinces();
    }

    @GetMapping("/{province_id}")
    public ResponseDTO<List<CityDTO>> findCitiesByProvince(@PathVariable final Long province_id) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new CustomException(Exceptions.UNAUTHORIZED);
        }

        return locationService.getCities(province_id);
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

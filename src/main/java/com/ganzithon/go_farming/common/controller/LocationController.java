package com.ganzithon.go_farming.common.controller;

import com.ganzithon.go_farming.common.dto.CityDTO;
import com.ganzithon.go_farming.common.dto.ProvinceDTO;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
        return locationService.getProvinces();
    }

    @GetMapping("/{province_id}")
    public ResponseDTO<List<CityDTO>> findCitiesByProvince(@PathVariable final Long province_id) {
        return locationService.getCities(province_id);
    }
}

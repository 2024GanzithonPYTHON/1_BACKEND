package com.ganzithon.go_farming.common.service;

import com.ganzithon.go_farming.common.repository.CityRepository;
import com.ganzithon.go_farming.common.repository.ProvinceRepository;
import com.ganzithon.go_farming.common.dto.CityDTO;
import com.ganzithon.go_farming.common.dto.ProvinceDTO;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.response.Responses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;

    public ResponseDTO<List<ProvinceDTO>> getProvinces() {
        List<ProvinceDTO> provinceDTOS = provinceRepository.findAll().stream().map(ProvinceDTO::of).toList();
        return new ResponseDTO<>(provinceDTOS, Responses.OK);
    }

    public ResponseDTO<List<CityDTO>> getCities(Long provinceId) {
        List<CityDTO> cityDTOS = cityRepository.findAllByProvinceId(provinceId).stream().map(CityDTO::of).toList();
        return new ResponseDTO<>(cityDTOS, Responses.OK);
    }

}

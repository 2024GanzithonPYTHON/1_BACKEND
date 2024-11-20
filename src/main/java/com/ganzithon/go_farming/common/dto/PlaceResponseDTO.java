package com.ganzithon.go_farming.common.dto;

import com.ganzithon.go_farming.common.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class PlaceResponseDTO {

    private Long id;
    private Long kakaoId;
    private String name;
    private Long categoryId;
    private String address;
    private String contact;
    private List<Map<String, Long>> keywords;

    public static PlaceResponseDTO of(Place place, List<Map<String, Long>> keywords){
        return new PlaceResponseDTO(place.getId(), place.getKakaoId(), place.getName(),
            place.getCategory().getId(), place.getAddress(), place.getContact(), keywords);}

}

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
    private String category;
    private String address;
    private String contact;
    private List<Map<String, Long>> keywords;
    private String imageUrl;
    private List<String> savedFolderColors;

    public static PlaceResponseDTO of(Place place, List<Map<String, Long>> keywords, String imageUrl, List<String> savedFolderColors){
        return new PlaceResponseDTO(place.getId(), place.getKakaoId(), place.getName(),
            place.getCategory().getName(), place.getAddress(), place.getContact(), keywords,
                imageUrl, savedFolderColors);}

}

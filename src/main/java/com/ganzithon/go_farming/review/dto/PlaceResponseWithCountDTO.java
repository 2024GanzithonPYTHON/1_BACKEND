package com.ganzithon.go_farming.review.dto;

import com.ganzithon.go_farming.common.dto.PlaceResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlaceResponseWithCountDTO {

    List<PlaceResponseDTO> places;
    int count;

}

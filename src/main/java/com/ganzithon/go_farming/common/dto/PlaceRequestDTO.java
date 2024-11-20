package com.ganzithon.go_farming.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceRequestDTO {

    private Long kakaoId;
    private String name;

}

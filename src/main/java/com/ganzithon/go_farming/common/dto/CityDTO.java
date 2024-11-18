package com.ganzithon.go_farming.common.dto;

import com.ganzithon.go_farming.common.domain.City;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityDTO {

    private Long id;
    private String name;

    public static CityDTO of(City city) {
        return new CityDTO(city.getId(), city.getName());
    }

}

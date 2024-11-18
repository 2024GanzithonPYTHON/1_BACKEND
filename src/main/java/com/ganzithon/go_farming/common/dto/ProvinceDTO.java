package com.ganzithon.go_farming.common.dto;

import com.ganzithon.go_farming.common.domain.Province;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProvinceDTO {

    private Long id;
    private String name;

    public static ProvinceDTO of(Province province) {
        return new ProvinceDTO(province.getId(), province.getName());
    }

}

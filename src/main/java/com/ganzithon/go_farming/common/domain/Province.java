package com.ganzithon.go_farming.common.domain;

import com.ganzithon.go_farming.common.dto.ProvinceDTO;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Province extends BaseEntity<Province> {

    private String name;

}

package com.ganzithon.go_farming.common.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class City extends BaseEntity<City> {

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    private String name;

}

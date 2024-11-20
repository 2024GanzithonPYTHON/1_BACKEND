package com.ganzithon.go_farming.common.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class City extends BaseEntity<City> {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "province_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Province province;
    private String name;

}

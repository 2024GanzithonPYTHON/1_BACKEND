package com.ganzithon.go_farming.common.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Category extends BaseEntity<Category> {

    private String name;

}

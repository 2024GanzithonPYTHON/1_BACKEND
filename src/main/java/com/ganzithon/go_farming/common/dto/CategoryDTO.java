package com.ganzithon.go_farming.common.dto;

import com.ganzithon.go_farming.common.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;

    public static CategoryDTO of(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }

}

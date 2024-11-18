package com.ganzithon.go_farming.common.service;

import com.ganzithon.go_farming.common.domain.Category;
import com.ganzithon.go_farming.common.domain.CategoryRepository;
import com.ganzithon.go_farming.common.dto.CategoryDTO;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.response.Responses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseDTO<List<CategoryDTO>> getCategoriesAll() {
        List<CategoryDTO> categoryDTOS = categoryRepository.findAll().stream().map(CategoryDTO::of).toList();
        return new ResponseDTO<>(categoryDTOS, Responses.OK);
    }

}

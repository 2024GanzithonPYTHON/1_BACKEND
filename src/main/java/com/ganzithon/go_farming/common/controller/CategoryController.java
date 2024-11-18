package com.ganzithon.go_farming.common.controller;

import com.ganzithon.go_farming.common.domain.Category;
import com.ganzithon.go_farming.common.dto.CategoryDTO;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public ResponseDTO<List<CategoryDTO>> findCategoriesAll() {
        return categoryService.getCategoriesAll();
    }

}

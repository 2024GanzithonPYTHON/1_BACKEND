package com.ganzithon.go_farming.common.repository;

import com.ganzithon.go_farming.common.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
    List<Category> findAll();


}

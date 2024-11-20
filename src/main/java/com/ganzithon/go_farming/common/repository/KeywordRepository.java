package com.ganzithon.go_farming.common.repository;

import com.ganzithon.go_farming.review.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findAllByCategoryId(Long categoryId);

}

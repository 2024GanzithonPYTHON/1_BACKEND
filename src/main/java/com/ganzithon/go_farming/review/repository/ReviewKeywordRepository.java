package com.ganzithon.go_farming.review;

import com.ganzithon.go_farming.review.domain.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {

    @Query("""
        SELECT rk.keyword.name, COUNT(rk.keyword.id)
        FROM ReviewKeyword rk
        WHERE rk.review.place.id = :placeId
        GROUP BY rk.keyword.name
        ORDER BY COUNT(rk.keyword.id) DESC
    """)
    List<Map<String, Integer>> findKeywordCountsByPlace(@Param("placeId") Long placeId);

}

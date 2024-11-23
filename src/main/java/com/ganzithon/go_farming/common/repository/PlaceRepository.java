package com.ganzithon.go_farming.common.repository;

import com.ganzithon.go_farming.common.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findById(Long placeId);
    Optional<Place> findByKakaoId(Long kakaoId);
    @Query("SELECT DISTINCT p FROM Place p JOIN p.reviews r WHERE " +
            "(:category IS NULL OR p.category.id = :category) AND " +
            "(:province IS NULL OR p.province.id = :province) AND " +
            "(:city IS NULL OR p.city.id = :city)")
    List<Place> findPlacesByFilters(@Param("category") Long categoryId,
                                    @Param("province") Long provinceId,
                                    @Param("city") Long cityId);
}

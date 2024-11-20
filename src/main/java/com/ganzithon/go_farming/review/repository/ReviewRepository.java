package com.ganzithon.go_farming.review.repository;

import com.ganzithon.go_farming.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByPlaceId(Long placeId);
    List<Review> findAllByPlaceIdAndUserUserId(Long placeId, Long userId);

}

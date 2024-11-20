package com.ganzithon.go_farming.review.repository;

import com.ganzithon.go_farming.review.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserUserIdAndReviewId(Long userId, Long reviewId);

}

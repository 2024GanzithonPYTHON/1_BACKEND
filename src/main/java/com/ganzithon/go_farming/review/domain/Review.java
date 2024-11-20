package com.ganzithon.go_farming.review.domain;

import com.ganzithon.go_farming.common.domain.BaseEntity;
import com.ganzithon.go_farming.common.domain.Place;
import com.ganzithon.go_farming.review.dto.ReviewRequestDTO;
import com.ganzithon.go_farming.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review extends BaseEntity<Review> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private Place place;

    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewPhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    private int likeCount = 0;
    private int visitedCount = 0;

    @CreatedDate
    private LocalDate createdAt;

    public Review(User user, Place place, ReviewRequestDTO reviewRequestDTO, int visitedCount) {
        this.user = user;
        this.place = place;
        this.content = reviewRequestDTO.getContent();
        this.visitedCount = visitedCount;
    }
}

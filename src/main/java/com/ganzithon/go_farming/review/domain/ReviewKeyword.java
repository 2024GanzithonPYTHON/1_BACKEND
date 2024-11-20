package com.ganzithon.go_farming.review.domain;

import com.ganzithon.go_farming.common.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewKeyword extends BaseEntity<ReviewKeyword> {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

}

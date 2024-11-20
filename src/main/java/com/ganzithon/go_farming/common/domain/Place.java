package com.ganzithon.go_farming.common.domain;

import com.ganzithon.go_farming.review.domain.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Place extends BaseEntity<Place> {

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "province_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Province province;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private City city;

    private String address;
    private String contact;
    private Long kakaoId;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

}

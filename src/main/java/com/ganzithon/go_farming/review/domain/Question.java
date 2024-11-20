package com.ganzithon.go_farming.review.domain;

import com.ganzithon.go_farming.common.domain.BaseEntity;
import com.ganzithon.go_farming.review.dto.QuestionRequestDTO;
import com.ganzithon.go_farming.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Question extends BaseEntity<Question> {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Review review;

    private String content;

    @CreatedDate
    private LocalDate createdAt;

    public Question(User user, Review review, QuestionRequestDTO questionRequestDTO) {
        this.user = user;
        this.review = review;
        this.content = questionRequestDTO.getContent();
    }

}

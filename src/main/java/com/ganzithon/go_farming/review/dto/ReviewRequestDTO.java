package com.ganzithon.go_farming.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewRequestDTO {

    private String content;
    private List<Long> keywords;

}

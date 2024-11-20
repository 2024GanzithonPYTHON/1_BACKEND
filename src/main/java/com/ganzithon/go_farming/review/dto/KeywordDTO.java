package com.ganzithon.go_farming.review.dto;

import com.ganzithon.go_farming.review.domain.Keyword;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeywordDTO {

    private Long id;
    private String name;

    public static KeywordDTO of(Keyword keyword) {return new KeywordDTO(keyword.getId(), keyword.getName());}

}

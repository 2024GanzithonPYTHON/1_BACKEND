package com.ganzithon.go_farming.common.service;

import com.ganzithon.go_farming.common.repository.KeywordRepository;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.response.Responses;
import com.ganzithon.go_farming.review.domain.Keyword;
import com.ganzithon.go_farming.review.dto.KeywordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public ResponseDTO<List<KeywordDTO>> findKeywordsByCategory(Long categortId) {
        List<KeywordDTO> result = keywordRepository.findAllByCategoryId(categortId)
                .stream().map(KeywordDTO::of).toList();
        return new ResponseDTO<>(result, Responses.OK);
    }

}

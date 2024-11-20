package com.ganzithon.go_farming.common.service;

import com.ganzithon.go_farming.common.dto.KakaoResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class KakaoService {

    @Value("${kakao.api-key}")
    private String API_KEY;

    public List<KakaoResponseDTO.Document> search(String query) {
        RestTemplate restTemplate = new RestTemplate();

        String BASE_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";
        String url = BASE_URL + "?query=" + query;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KakaoResponseDTO.class
        );

        System.out.println(response.getBody().toString());

        // 응답이 없는 경우 처리
        if (response.getBody() == null || response.getBody().getDocuments() == null) {
            throw new IllegalStateException("Kakao API 응답이 비어 있습니다.");
        }

        return response.getBody().getDocuments();
    }

}

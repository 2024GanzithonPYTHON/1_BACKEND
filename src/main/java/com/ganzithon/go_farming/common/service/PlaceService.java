package com.ganzithon.go_farming.common.service;

import com.ganzithon.go_farming.common.domain.Category;
import com.ganzithon.go_farming.common.domain.City;
import com.ganzithon.go_farming.common.domain.Province;
import com.ganzithon.go_farming.common.repository.CityRepository;
import com.ganzithon.go_farming.common.domain.Place;
import com.ganzithon.go_farming.common.repository.ProvinceRepository;
import com.ganzithon.go_farming.common.dto.KakaoResponseDTO;
import com.ganzithon.go_farming.common.dto.PlaceRequestDTO;
import com.ganzithon.go_farming.common.dto.PlaceResponseDTO;
import com.ganzithon.go_farming.common.exception.CustomException;
import com.ganzithon.go_farming.common.exception.Exceptions;
import com.ganzithon.go_farming.common.repository.CategoryRepository;
import com.ganzithon.go_farming.common.repository.PlaceRepository;
import com.ganzithon.go_farming.common.response.ResponseDTO;
import com.ganzithon.go_farming.common.response.Responses;
import com.ganzithon.go_farming.review.dto.KeywordCountDTO;
import com.ganzithon.go_farming.review.repository.ReviewKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final KakaoService kakaoService;
    private final CategoryRepository categoryRepository;
    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;

    @Transactional
    public ResponseDTO<PlaceResponseDTO> findPlaceByKakaoId(PlaceRequestDTO dto) {
        Optional<Place> tempPlace = placeRepository.findByKakaoId(dto.getKakaoId());
        PlaceResponseDTO result;
        if (tempPlace.isPresent()) { // 이미 우리 서비스에 저장된 장소면
            Place place = tempPlace.get();
            List<KeywordCountDTO> keywords = reviewKeywordRepository.findKeywordCountsByPlace(place.getId());
            List<Map<String, Long>> rankedKeywords = sortTopKeywords(keywords);
            result = PlaceResponseDTO.of(place, rankedKeywords);
        } else { // 아직 없는 장소면 저장
            List<KakaoResponseDTO.Document> docs = kakaoService.search(dto.getName());

            Optional<KakaoResponseDTO.Document> target = docs.stream()
                    .filter(doc -> ((Long) Long.parseLong(doc.getId())).equals(dto.getKakaoId())).findFirst();

            if (target.isEmpty()) {throw new CustomException(Exceptions.PLACE_NOT_FOUND);}
            KakaoResponseDTO.Document document = target.get();
            Category category = categoryRepository.findByName(document.getCategoryGroupName());

            String[] location = document.getRoadAddressName().split(" ");
            Optional<Province> tempProvince = provinceRepository.findByName(location[0]);
            Province province;
            province = tempProvince.orElseGet(() -> provinceRepository.save(new Province(location[0])));

            Optional<City> tempCity = cityRepository.findByName(location[1]);
            City city = tempCity.orElseGet(() -> cityRepository.save(new City(province, location[1])));

            Place place = new Place(document.getPlaceName(), category, province, city, document.getRoadAddressName(),
                    document.getPhone(), Long.parseLong(document.getId()), new ArrayList<>());
            placeRepository.save(place);

            result = PlaceResponseDTO.of(place, new ArrayList<>());
        }
        return new ResponseDTO<>(result, Responses.OK);

    }

    public ResponseDTO<List<PlaceResponseDTO>> findPlaceByCondition(Long categoryId, Long provinceId, Long cityId) {
        List<Place> places = placeRepository.findPlacesByFilters(categoryId, provinceId, cityId);
        List<PlaceResponseDTO> result = places.stream().map(place -> {
            List<KeywordCountDTO> keywords = reviewKeywordRepository.findKeywordCountsByPlace(place.getId());
            List<Map<String, Long>> rankedKeywords = sortTopKeywords(keywords);
            return PlaceResponseDTO.of(place, rankedKeywords);
        }).toList();

        return new ResponseDTO<>(result, Responses.OK);
    }

    public ResponseDTO<PlaceResponseDTO> findPlaceById(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(Exceptions.PLACE_NOT_EXIST));

        List<KeywordCountDTO> keywords = reviewKeywordRepository.findKeywordCountsByPlace(placeId);
        List<Map<String, Long>> rankedKeywords = sortTopKeywords(keywords);

        return new ResponseDTO<>(PlaceResponseDTO.of(place, rankedKeywords), Responses.OK);
    }

    private static List<Map<String, Long>> sortTopKeywords(List<KeywordCountDTO> keywords) {
        return keywords.stream()
                .sorted((k1, k2) -> k2.getCount().compareTo(k1.getCount())) // count 기준 내림차순 정렬
                .limit(3) // 상위 3개만 선택
                .map(dto -> Map.of(dto.getName(), dto.getCount())) // DTO를 Map으로 변환
                .collect(Collectors.toList());
    }
}

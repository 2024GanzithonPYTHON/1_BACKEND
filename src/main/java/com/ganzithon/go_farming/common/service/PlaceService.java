package com.ganzithon.go_farming.common.service;

import com.ganzithon.go_farming.bookmark.Folder;
import com.ganzithon.go_farming.bookmark.FolderRepository;
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
import com.ganzithon.go_farming.review.domain.Review;
import com.ganzithon.go_farming.review.domain.ReviewPhoto;
import com.ganzithon.go_farming.review.dto.KeywordCountDTO;
import com.ganzithon.go_farming.review.repository.ReviewKeywordRepository;
import com.ganzithon.go_farming.review.repository.ReviewRepository;
import com.ganzithon.go_farming.user.User;
import com.ganzithon.go_farming.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private final ReviewRepository reviewRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDTO<PlaceResponseDTO> findPlaceByKakaoId(PlaceRequestDTO dto, String username) {
        Optional<Place> tempPlace = placeRepository.findByKakaoId(dto.getKakaoId());
        PlaceResponseDTO result;
        if (tempPlace.isPresent()) { // 이미 우리 서비스에 저장된 장소면
            Place place = tempPlace.get();
            Long placeId = place.getId();
            result = PlaceResponseDTO.of(place, getRankedKeywords(placeId), getRandomImageUrl(placeId),
                    getSavedFolderColors(placeId, username));
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

            result = PlaceResponseDTO.of(place, new ArrayList<>(), null, new ArrayList<>());
        }
        return new ResponseDTO<>(result, Responses.OK);

    }

    public ResponseDTO<List<PlaceResponseDTO>> findPlaceByCondition(Long categoryId, Long provinceId, Long cityId, String username) {
        List<Place> places = placeRepository.findPlacesByFilters(categoryId, provinceId, cityId);
        List<PlaceResponseDTO> result = places.stream()
                .map(place -> PlaceResponseDTO.of(place, getRankedKeywords(place.getId()),
                        getRandomImageUrl(place.getId()), getSavedFolderColors(place.getId(), username))).toList();

        return new ResponseDTO<>(result, Responses.OK);
    }

    public ResponseDTO<PlaceResponseDTO> findPlaceById(Long placeId, String username) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(Exceptions.PLACE_NOT_EXIST));

        return new ResponseDTO<>(PlaceResponseDTO.of(place, getRankedKeywords(placeId), getRandomImageUrl(placeId),
                getSavedFolderColors(placeId, username)), Responses.OK);
    }

    private static List<Map<String, Long>> sortTopKeywords(List<KeywordCountDTO> keywords) {
        return keywords.stream()
                .sorted((k1, k2) -> k2.getCount().compareTo(k1.getCount())) // count 기준 내림차순 정렬
                .limit(3) // 상위 3개만 선택
                .map(dto -> Map.of(dto.getName(), dto.getCount())) // DTO를 Map으로 변환
                .collect(Collectors.toList());
    }

    private List<Map<String, Long>> getRankedKeywords(Long placeId) {
        List<KeywordCountDTO> keywords = reviewKeywordRepository.findKeywordCountsByPlace(placeId);
        return sortTopKeywords(keywords);
    }

    private String getRandomImageUrl(Long placeId) {
        List<String> reviewImageUrls = reviewRepository.findAllByPlaceId(placeId).stream()
                .flatMap(review -> review.getPhotos().stream().map(ReviewPhoto::getUrl))
                .toList();
        Random random = new Random();
        int randomNumber = random.nextInt(reviewImageUrls.size());
        return reviewImageUrls.get(randomNumber);
    }

    private List<String> getSavedFolderColors(Long placeId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(Exceptions.MEMBER_NOT_EXIST));
        List<String> result = folderRepository.findByUserUserId(user.getUserId())
                .stream().map(Folder::getColor).toList();
        return result;

    }
}

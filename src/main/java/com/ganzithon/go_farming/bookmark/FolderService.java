package com.ganzithon.go_farming.bookmark;

//import com.ganzithon.go_farming.location.Location;
import com.ganzithon.go_farming.common.domain.Place;
import com.ganzithon.go_farming.common.repository.PlaceRepository;
import com.ganzithon.go_farming.user.User;
import com.ganzithon.go_farming.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;

    private final UserRepository userRepository;
    private static final List<String> VALID_COLORS = Arrays.asList(
            "#FF3434", // 빨강
            "#FF9D00", // 주황
            "#FFEE00", // 노랑
            "#53E300", // 초록
            "#005EFF", // 파랑
            "#9100FF", // 보라
            "#FF55AD" // 분홍
            );
    private final PlaceRepository placeRepository;

    public FolderService(FolderRepository folderRepository, UserRepository userRepository, PlaceRepository placeRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
    }

    // 사용자명을 기반으로 폴더 리스트 반환
    public List<Folder> getFoldersByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return folderRepository.findByUserUserId(user.getUserId());
    }

    // 사용자명을 기반으로 새 폴더 생성
    public Folder createFolder(String username, Folder folder) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!VALID_COLORS.contains(folder.getColor().toUpperCase())) {
            throw new IllegalArgumentException("유효하지 않은 색상입니다: " + folder.getColor());
        }

        folder.setUser(user);
        folder.setColor(folder.getColor().toUpperCase()); // 색상 대문자로 설정
        return folderRepository.save(folder);
    }

    // 폴더 삭제
    public void deleteFolder(Long folderId) {
        if (!folderRepository.existsById(folderId)) {
            throw new IllegalArgumentException("Folder not found with id: " + folderId);
        }
        folderRepository.deleteById(folderId);
    }

    // 폴더 이름 수정
    public Folder updateFolderName(Long folderId, String newName) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found with id: " + folderId));
        folder.setFolderName(newName);
        return folderRepository.save(folder);
    }

   // 폴더에 Place 추가
    public Place addPlaceToFolder(Long folderId, Long placeId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found with id: " + folderId));
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + placeId));

        // 중복 추가 방지
        if (folder.getSavedLocations().contains(place)) {
            throw new IllegalArgumentException("This place is already added to the folder.");
        }

        folder.getSavedLocations().add(place);
        return placeRepository.save(place);
    }

    // 폴더에서 Place 제거
    public void removePlaceFromFolder(Long folderId, Long placeId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found with id: " + folderId));
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + placeId));

        if (!folder.getSavedLocations().remove(place)) {
            throw new IllegalArgumentException("Place not associated with folder.");
        }

        folderRepository.save(folder);
    }

    // 폴더 안의 Place 조회
    public List<Place> getPlacesInFolder(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found with id: " + folderId));

        return folder.getSavedLocations();
    }
}

package com.ganzithon.go_farming.bookmark;

//import com.ganzithon.go_farming.location.Location;
import com.ganzithon.go_farming.user.User;
import com.ganzithon.go_farming.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final SavedLocationRepository savedLocationRepository;
    private final UserRepository userRepository;
    private static final List<String> VALID_COLORS = Arrays.asList("RED", "ORANGE", "YELLOW", "GREEN", "BLUE", "PURPLE", "PINK");

    public FolderService(FolderRepository folderRepository, SavedLocationRepository savedLocationRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.savedLocationRepository = savedLocationRepository;
        this.userRepository = userRepository;
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

   /* // 폴더에 위치 추가
    public SavedLocation addLocationToFolder(Long folderId, Location location) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found with id: " + folderId));

        SavedLocation savedLocation = new SavedLocation();
        savedLocation.setFolder(folder);
        savedLocation.setLocation(location);
        return savedLocationRepository.save(savedLocation);
    }

    // 폴더에서 위치 제거
    public void removeLocationFromFolder(Long folderId, Long locationId) {
        if (!savedLocationRepository.existsByFolderFolderIdAndLocationLocationId(folderId, locationId)) {
            throw new IllegalArgumentException("SavedLocation not found with folderId: " + folderId + " and locationId: " + locationId);
        }
        savedLocationRepository.deleteByFolderFolderIdAndLocationLocationId(folderId, locationId);
    }

    // 폴더 안의 위치 조회
    public List<Location> getLocationsInFolder(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found with id: " + folderId));

        return savedLocationRepository.findByFolderFolderId(folderId)
                .stream()
                .map(SavedLocation::getLocation)
                .toList();
    }*/
}

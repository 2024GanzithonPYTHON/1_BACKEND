package com.ganzithon.go_farming.bookmark;

//import com.ganzithon.go_farming.location.Location;
import com.ganzithon.go_farming.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository; // FolderRepository 의존성
    private final SavedLocationRepository savedLocationRepository; // SavedLocationRepository 의존성

    // 의존성 주입을 통한 생성자
    public FolderService(FolderRepository folderRepository, SavedLocationRepository savedLocationRepository) {
        this.folderRepository = folderRepository;
        this.savedLocationRepository = savedLocationRepository;
    }

    // 사용자 ID를 기반으로 폴더 리스트를 반환
    public List<Folder> getFoldersByUserId(Long userId) {
        return folderRepository.findByUserUserId(userId);
    }

    // 새 폴더 생성 (User 엔티티와 연관)
    public Folder createFolder(Folder folder, User user) {
        folder.setUser(user); // User 엔티티 설정
        return folderRepository.save(folder); // 폴더 저장
    }

    // 폴더 삭제 (존재하지 않으면 예외 발생)
    public void deleteFolder(Long folderId) {
        if (!folderRepository.existsById(folderId)) {
            throw new IllegalArgumentException("Folder not found with id: " + folderId);
        }
        folderRepository.deleteById(folderId); // 폴더 삭제
    }

    // 폴더에 위치 추가
   /* public SavedLocation addLocationToFolder(Long folderId, Location location) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found with id: " + folderId)); // 폴더 조회

        SavedLocation savedLocation = new SavedLocation(); // 새 SavedLocation 생성
        savedLocation.setFolder(folder); // 폴더 설정
        savedLocation.setLocation(location); // 위치 설정

        return savedLocationRepository.save(savedLocation); // SavedLocation 저장
    }*/

    // 폴더에서 위치 제거
    public void removeLocationFromFolder(Long folderId, Long locationId) {
        if (!savedLocationRepository.existsByFolderFolderIdAndLocationLocationId(folderId, locationId)) {
            throw new IllegalArgumentException("SavedLocation not found with folderId: " + folderId + " and locationId: " + locationId);
        }
        savedLocationRepository.deleteByFolderFolderIdAndLocationLocationId(folderId, locationId); // SavedLocation 삭제
    }
}

package com.ganzithon.go_farming.bookmark;

//import com.ganzithon.go_farming.location.Location;
import com.ganzithon.go_farming.user.User;
import com.ganzithon.go_farming.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService; // FolderService 의존성
    private final UserRepository userRepository; // UserRepository 의존성

    @Autowired // 의존성 주입
    public FolderController(FolderService folderService, UserRepository userRepository) {
        this.folderService = folderService;
        this.userRepository = userRepository;
    }

    // 특정 사용자 ID로 폴더 리스트 조회
    @GetMapping("/user/{userId}")
    public List<Folder> getFoldersByUserId(@PathVariable Long userId) {
        return folderService.getFoldersByUserId(userId);
    }

    // 새로운 폴더 생성
    @PostMapping("/user/{userId}")
    public Folder createFolder(@PathVariable Long userId, @RequestBody Folder folder) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId)); // 사용자 확인
        return folderService.createFolder(folder, user);
    }

    // 폴더 삭제
    @DeleteMapping("/{folderId}")
    public void deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
    }

    // 폴더에 위치 추가
    /*@PostMapping("/{folderId}/locations")
    public SavedLocation addLocation(@PathVariable Long folderId, @RequestBody Location location) {
        return folderService.addLocationToFolder(folderId, location);
    }*/

    // 폴더에서 위치 제거
    @DeleteMapping("/{folderId}/locations/{locationId}")
    public void removeLocation(@PathVariable Long folderId, @PathVariable Long locationId) {
        folderService.removeLocationFromFolder(folderId, locationId);
    }
}

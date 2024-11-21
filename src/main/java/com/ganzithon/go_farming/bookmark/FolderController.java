package com.ganzithon.go_farming.bookmark;

//import com.ganzithon.go_farming.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    // JWT 기반으로 폴더 리스트 조회
    @GetMapping
    public ResponseEntity<List<Folder>> getFolders() {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(folderService.getFoldersByUsername(username));
    }

    // JWT 기반으로 새로운 폴더 생성
    @PostMapping
    public ResponseEntity<?> createFolder(@RequestBody Folder folder) {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(folderService.createFolder(username, folder));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // JWT 기반으로 폴더 삭제
    @DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteFolder(@PathVariable Long folderId) {
        try {
            folderService.deleteFolder(folderId);
            return ResponseEntity.ok("폴더가 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // JWT 기반으로 폴더 이름 수정
    @PutMapping("/{folderId}")
    public ResponseEntity<?> updateFolderName(@PathVariable Long folderId, @RequestBody Map<String, String> payload) {
        String newName = payload.get("newName");
        if (newName == null || newName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("새 폴더 이름을 제공하세요.");
        }
        try {
            Folder updatedFolder = folderService.updateFolderName(folderId, newName);
            return ResponseEntity.ok(updatedFolder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // JWT 기반으로 폴더에 위치 추가
    /*@PostMapping("/{folderId}/locations")
    public ResponseEntity<?> addLocation(@PathVariable Long folderId, @RequestBody Location location) {
        try {
            folderService.addLocationToFolder(folderId, location);
            return ResponseEntity.status(HttpStatus.CREATED).body("위치가 폴더에 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // JWT 기반으로 폴더에서 위치 제거
    @DeleteMapping("/{folderId}/locations/{locationId}")
    public ResponseEntity<?> removeLocation(@PathVariable Long folderId, @PathVariable Long locationId) {
        try {
            folderService.removeLocationFromFolder(folderId, locationId);
            return ResponseEntity.ok("위치가 폴더에서 제거되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 폴더 안의 위치 조회
    @GetMapping("/{folderId}/locations")
    public ResponseEntity<?> getLocationsInFolder(@PathVariable Long folderId) {
        try {
            List<Location> locations = folderService.getLocationsInFolder(folderId);
            return ResponseEntity.ok(locations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }*/

    // 현재 인증된 사용자명을 가져오는 헬퍼 메서드
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName();
    }
}

package com.ganzithon.go_farming.bookmark;

import com.ganzithon.go_farming.location.Location;
import com.ganzithon.go_farming.user.User;
import com.ganzithon.go_farming.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;
    private final UserRepository userRepository;

    @Autowired
    public FolderController(FolderService folderService, UserRepository userRepository) {
        this.folderService = folderService;
        this.userRepository = userRepository;
    }

    // 세션 기반으로 폴더 리스트 조회
    @GetMapping
    public ResponseEntity<?> getFolders(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        List<Folder> folders = folderService.getFoldersByUserId(userId);
        return ResponseEntity.ok(folders);
    }

    // 세션 기반으로 새로운 폴더 생성
    @PostMapping
    public ResponseEntity<?> createFolder(@RequestBody Folder folder, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Folder createdFolder = folderService.createFolder(folder, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFolder);
    }

    // 세션 기반으로 폴더 삭제
    @DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteFolder(@PathVariable Long folderId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        folderService.deleteFolder(folderId); // Optional: userId를 사용한 검증 추가 가능
        return ResponseEntity.ok("폴더가 삭제되었습니다.");
    }

    // 세션 기반으로 폴더에 위치 추가
    /*@PostMapping("/{folderId}/locations")
    public ResponseEntity<?> addLocation(@PathVariable Long folderId, @RequestBody Location location, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        try {
            folderService.addLocationToFolder(folderId, location);
            return ResponseEntity.status(HttpStatus.CREATED).body("위치가 폴더에 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/

    // 세션 기반으로 폴더에서 위치 제거
    @DeleteMapping("/{folderId}/locations/{locationId}")
    public ResponseEntity<?> removeLocation(@PathVariable Long folderId, @PathVariable Long locationId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        try {
            folderService.removeLocationFromFolder(folderId, locationId);
            return ResponseEntity.ok("위치가 폴더에서 제거되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

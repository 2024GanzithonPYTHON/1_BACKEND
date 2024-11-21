package com.ganzithon.go_farming.mypage;

import com.ganzithon.go_farming.review.domain.Question;
import com.ganzithon.go_farming.review.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // 닉네임 수정
    @PatchMapping("/nickname")
    public ResponseEntity<?> updateNickname(@RequestBody Map<String, String> payload) {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        String nickname = payload.get("nickname");
        return ResponseEntity.ok(userProfileService.updateNickname(username, nickname));
    }

    // 프로필 사진 URL 수정
    @PatchMapping("/profile-picture")
    public ResponseEntity<?> updateProfilePicture(@RequestBody Map<String, String> payload) {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        String profilePictureUrl = payload.get("profilePictureUrl");
        return ResponseEntity.ok(userProfileService.updateProfilePicture(username, profilePictureUrl));
    }

    // 비밀번호 수정
    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> payload) {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        String password = payload.get("password");
        return ResponseEntity.ok(userProfileService.updatePassword(username, password));
    }

    // 닉네임 조회
    @GetMapping("/nickname")
    public ResponseEntity<?> getNickname() {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        return ResponseEntity.ok(userProfileService.getNickname(username));
    }

    // 프로필 사진 URL 조회
    @GetMapping("/profile-picture")
    public ResponseEntity<?> getProfilePicture() {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        return ResponseEntity.ok(userProfileService.getProfilePicture(username));
    }

    // 연령대 조회
    @GetMapping("/age-group")
    public ResponseEntity<?> getAgeGroup() {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        return ResponseEntity.ok(userProfileService.getAgeGroup(username));
    }

    // 거주지 조회
    @GetMapping("/region")
    public ResponseEntity<?> getRegion() {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        return ResponseEntity.ok(userProfileService.getRegion(username));
    }

    // 작성한 리뷰 조회
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getUserReviews() {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(userProfileService.getUserReviews(username));
    }

    // 작성한 질문 조회
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getUserQuestions() {
        String username = getCurrentUsername();
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(userProfileService.getUserQuestions(username));
    }

    // 현재 인증된 사용자명을 가져오는 헬퍼 메서드
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName();
    }
}

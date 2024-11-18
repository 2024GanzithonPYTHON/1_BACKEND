package com.ganzithon.go_farming.mypage;

import com.ganzithon.go_farming.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // 닉네임 수정
    @PatchMapping("/nickname")
    public ResponseEntity<?> updateNickname(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        String nickname = payload.get("nickname"); // JSON에서 "nickname" 키 가져오기
        User updatedUser = userProfileService.updateNickname(userId, nickname);
        return ResponseEntity.ok(updatedUser);
    }

    // 프로필 사진 URL 수정
    @PatchMapping("/profile-picture")
    public ResponseEntity<?> updateProfilePicture(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        String profilePictureUrl = payload.get("profilePictureUrl"); // JSON에서 "profilePictureUrl" 키 가져오기
        User updatedUser = userProfileService.updateProfilePicture(userId, profilePictureUrl);
        return ResponseEntity.ok(updatedUser);
    }

    // 비밀번호 수정
    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        String password = payload.get("password"); // JSON에서 "password" 키 가져오기
        User updatedUser = userProfileService.updatePassword(userId, password);
        return ResponseEntity.ok(updatedUser);
    }
    /*// 작성한 리뷰 조회
    @GetMapping("/reviews")
    public ResponseEntity<?> getUserReviews(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        return ResponseEntity.ok(userProfileService.getUserReviews(userId));
    }*/

    // 작성한 질문 조회
    /*@GetMapping("/questions")
    public ResponseEntity<?> getUserQuestions(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        return ResponseEntity.ok(userProfileService.getUserQuestions(userId));
    }*/
}

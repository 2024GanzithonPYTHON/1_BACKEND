package com.ganzithon.go_farming.mypage;

import com.ganzithon.go_farming.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // 닉네임 수정
    @PatchMapping("/nickname")
    public ResponseEntity<?> updateNickname(@RequestParam String nickname, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        User updatedUser = userProfileService.updateNickname(userId, nickname);
        return ResponseEntity.ok(updatedUser);
    }

    // 프로필 사진 URL 수정
    @PatchMapping("/profile-picture")
    public ResponseEntity<?> updateProfilePicture(@RequestParam String profilePictureUrl, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        User updatedUser = userProfileService.updateProfilePicture(userId, profilePictureUrl);
        return ResponseEntity.ok(updatedUser);
    }

    // 비밀번호 수정
    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestParam String password, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        User updatedUser = userProfileService.updatePassword(userId, password);
        return ResponseEntity.ok(updatedUser);
    }

    // 작성한 리뷰 조회
    /*
    @GetMapping("/reviews")
    public ResponseEntity<?> getUserReviews(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = new User();
        user.setUserId(userId);
        return ResponseEntity.ok(reviewService.getUserReviews(user));
    }
    */

    // 작성한 질문 조회
    /*
    @GetMapping("/questions")
    public ResponseEntity<?> getUserQuestions(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = new User();
        user.setUserId(userId);
        return ResponseEntity.ok(questionService.getUserQuestions(user));
    }
    */
}

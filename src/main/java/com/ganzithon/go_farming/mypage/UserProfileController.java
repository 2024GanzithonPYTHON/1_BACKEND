package com.ganzithon.go_farming.mypage;

import com.ganzithon.go_farming.question.Question;
import com.ganzithon.go_farming.question.QuestionService;
import com.ganzithon.go_farming.review.Review;
import com.ganzithon.go_farming.review.ReviewService;
import com.ganzithon.go_farming.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private QuestionService questionService;

    // 닉네임 수정
    @PatchMapping("/{userId}/nickname")
    public User updateNickname(@PathVariable Long userId, @RequestParam String nickname) {
        return userProfileService.updateNickname(userId, nickname);
    }

    // 프로필 사진 URL 수정
    @PatchMapping("/{userId}/profile-picture")
    public User updateProfilePicture(@PathVariable Long userId, @RequestParam String profilePictureUrl) {
        return userProfileService.updateProfilePicture(userId, profilePictureUrl);
    }

    // 비밀번호 수정
    @PatchMapping("/{userId}/password")
    public User updatePassword(@PathVariable Long userId, @RequestParam String password) {
        return userProfileService.updatePassword(userId, password);
    }

    // 작성한 리뷰 조회
    @GetMapping("/{userId}/reviews")
    public List<Review> getUserReviews(@PathVariable Long userId) {
        User user = new User();
        user.setUserId(userId); // 필요 시 User 엔티티를 직접 로드하도록 수정 가능
        return reviewService.getUserReviews(user);
    }

    // 작성한 질문 조회
    @GetMapping("/{userId}/questions")
    public List<Question> getUserQuestions(@PathVariable Long userId) {
        User user = new User();
        user.setUserId(userId); // 필요 시 User 엔티티를 직접 로드하도록 수정 가능
        return questionService.getUserQuestions(user);
    }
}
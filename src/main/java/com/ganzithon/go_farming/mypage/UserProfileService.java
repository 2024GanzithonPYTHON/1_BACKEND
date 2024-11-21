package com.ganzithon.go_farming.mypage;

import com.ganzithon.go_farming.review.domain.Question;
import com.ganzithon.go_farming.review.domain.Review;
import com.ganzithon.go_farming.user.User;
import com.ganzithon.go_farming.user.UserRepository;
import com.ganzithon.go_farming.review.repository.QuestionRepository;
import com.ganzithon.go_farming.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // 닉네임 수정
    public User updateNickname(String username, String nickname) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.setNickname(nickname);
        return userRepository.save(user);
    }

    // 프로필 사진 URL 수정
    public User updateProfilePicture(String username, String profilePictureUrl) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.setProfilePicture(profilePictureUrl);
        return userRepository.save(user);
    }

    // 비밀번호 수정
    public User updatePassword(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.setPassword(password);
        return userRepository.save(user);
    }

    // 닉네임 조회
    public String getNickname(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user.getNickname();
    }

    // 프로필 사진 URL 조회
    public String getProfilePicture(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user.getProfilePicture();
    }

    // 연령대 조회
    public int getAgeGroup(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user.getAgeGroup();
    }

    // 거주지 조회
    public String getRegion(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user.getRegion();
    }

    // 특정 사용자가 작성한 리뷰 조회
    public List<Review> getUserReviews(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return reviewRepository.findAllByPlaceIdAndUserUserId(null, user.getUserId()); // placeId가 null일 경우 모든 리뷰 반환
    }

    // 특정 사용자가 작성한 질문 조회
    public List<Question> getUserQuestions(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return questionRepository.findAllByReviewId(user.getUserId()); // userId를 reviewId로 사용
    }
}

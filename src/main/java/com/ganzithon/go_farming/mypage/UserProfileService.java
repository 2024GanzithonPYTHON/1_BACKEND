package com.ganzithon.go_farming.mypage;

//import com.ganzithon.go_farming.mypage.dto.Review;
//import com.ganzithon.go_farming.mypage.dto.Question;
import com.ganzithon.go_farming.user.User;
import com.ganzithon.go_farming.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private ReviewRepository reviewRepository;

    //@Autowired
    //private QuestionRepository questionRepository;

    // 닉네임 수정
    public User updateNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setNickname(nickname);
        return userRepository.save(user);
    }

    // 프로필 사진 URL 수정
    public User updateProfilePicture(Long userId, String profilePictureUrl) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfilePicture(profilePictureUrl);
        return userRepository.save(user);
    }

    // 비밀번호 수정
    public User updatePassword(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(password);
        return userRepository.save(user);
    }

    // 특정 사용자가 작성한 리뷰 조회
    /*public List<Review> getUserReviews(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    // 특정 사용자가 작성한 질문 조회
    public List<Question> getUserQuestions(Long userId) {
        return questionRepository.findByUserId(userId);
    }*/
}

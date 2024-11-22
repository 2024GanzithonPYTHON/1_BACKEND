package com.ganzithon.go_farming.user;

import com.ganzithon.go_farming.common.service.S3Service;
import com.ganzithon.go_farming.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private S3Service s3Service; // S3에 사진 업로드를 처리하는 서비스

    @Transactional
    public String uploadProfilePicture(MultipartFile profilePicture) {
        // S3에 파일 업로드 및 URL 반환
        return s3Service.uploadFile(profilePicture, "profile-pictures");
    }


    @Transactional
    public User registerUser(User user) {
        // 아이디(username) 중복 확인
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 닉네임(nickname) 중복 확인
        if (userRepository.existsByNickname(user.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 사용자 저장
        return userRepository.save(user);
    }

    public String loginUser(String username, String password) {
        // 사용자 조회
        Optional<User> user = userRepository.findByUsername(username);

        // 비밀번호 검증
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            // JWT 토큰 생성
            return jwtTokenUtil.generateToken(username);
        }

        throw new IllegalArgumentException("잘못된 아이디 또는 비밀번호입니다.");
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

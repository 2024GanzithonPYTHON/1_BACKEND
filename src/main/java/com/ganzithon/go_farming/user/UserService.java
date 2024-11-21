package com.ganzithon.go_farming.user;

import com.ganzithon.go_farming.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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

package com.ganzithon.go_farming.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service // Spring 서비스 계층 어노테이션
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional // 트랜잭션 관리
    public User registerUser(User user) {
        // username 또는 nickname 중복 확인
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByNickname(user.getNickname())) {
            throw new IllegalArgumentException("아이디나 닉네임이 이미 존재합니다.");
        }
        // 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        // 비밀번호 매칭 확인
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty(); // 매칭되지 않으면 빈 결과 반환
    }
}

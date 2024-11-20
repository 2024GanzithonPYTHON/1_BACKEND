package com.ganzithon.go_farming.user;

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


    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}

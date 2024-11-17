package com.ganzithon.go_farming.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // username을 통한 사용자 조회
    boolean existsByUsername(String username); // username 존재 여부 확인
    boolean existsByNickname(String nickname); // nickname 존재 여부 확인
}

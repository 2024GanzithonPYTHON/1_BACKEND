package com.ganzithon.go_farming.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController // RESTful 컨트롤러 어노테이션
@RequestMapping("/users") // 공통 URL 경로 설정
@Validated // 유효성 검사를 위한 어노테이션
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register") // 회원가입 API 엔드포인트
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto userDto) {
        // 비밀번호 일치 확인
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
        try {
            // DTO를 엔티티로 변환 후 저장
            User user = User.builder()
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .nickname(userDto.getNickname())
                    .profilePicture(userDto.getProfilePicture())
                    .ageGroup(userDto.getAgeGroup())
                    .region(userDto.getRegion())
                    .build();

            User registeredUser = userService.registerUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "회원가입이 완료되었습니다.");
            response.put("userId", registeredUser.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // 성공 응답
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage()); // 중복 예외 처리
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회원가입 실패."); // 기타 예외 처리
        }
    }

    @PostMapping("/login") // 로그인 API 엔드포인트
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<User> user = userService.loginUser(username, password);
        // 로그인 성공 시 응답 메시지 생성
        if (user.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "로그인 성공");
            response.put("username", user.get().getUsername());
            return ResponseEntity.ok(response); // 성공 응답
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증 실패."); // 인증 실패
        }
    }
}

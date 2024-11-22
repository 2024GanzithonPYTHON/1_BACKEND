package com.ganzithon.go_farming.user;

import com.ganzithon.go_farming.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<?> registerUser(@RequestPart("user") UserRegistrationDto userDto,
                                          @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }
        try {
            // 프로필 사진 처리
            String profilePictureUrl = null;
            if (profilePicture != null) {
                profilePictureUrl = userService.uploadProfilePicture(profilePicture);
            }

            User user = User.builder()
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .nickname(userDto.getNickname())
                    .profilePicture(profilePictureUrl) // 저장된 URL 사용
                    .ageGroup(userDto.getAgeGroup())
                    .region(userDto.getRegion())
                    .build();
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공: " + registeredUser.getUserId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 로그인 성공 시 JWT 토큰 생성
            String token = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok().body("로그인 성공. Token: " + token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        // 클라이언트에게 JWT 삭제를 요청하는 메시지 반환
        return ResponseEntity.ok().body("로그아웃 성공. 클라이언트에서 토큰을 삭제해주세요.");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 필요");
        }

        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보 없음");
        }
    }
}

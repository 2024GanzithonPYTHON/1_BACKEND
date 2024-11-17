package com.ganzithon.go_farming.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Lombok을 사용해 getter, setter, toString 등을 자동 생성
public class UserRegistrationDto {

    @NotBlank(message = "아이디를 작성하시오") // 빈 값 허용하지 않음
    private String username;

    @NotBlank(message = "패스워드를 작성하시오") // 빈 값 허용하지 않음
    private String password;

    @NotBlank(message = "패스워드 확인을 작성하시오") // 빈 값 허용하지 않음
    private String passwordConfirm;

    @NotBlank(message = "닉네임을 작성하시오") // 빈 값 허용하지 않음
    private String nickname;

    private String profilePicture; // 프로필 사진 (옵션)

    @NotNull(message = "나이대를 작성하시오") // null 허용하지 않음
    private int ageGroup;

    @NotBlank(message = "지역을 작성하시오") // 빈 값 허용하지 않음
    private String region;
}
